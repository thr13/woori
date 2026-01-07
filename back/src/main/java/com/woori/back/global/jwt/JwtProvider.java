package com.woori.back.global.jwt;

import com.woori.back.domain.auth.entity.Token;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@Component
public class JwtProvider {

    private final JwtProperties properties;
    private final SecretKey secretKey;

    public JwtProvider(JwtProperties properties) {
        this.properties = properties;
        this.secretKey = Keys.hmacShaKeyFor(properties.getSecret().getBytes(StandardCharsets.UTF_8));
    }

    // accessToken 생성
    public String createAccessToken(Long userId, String role) {
        log.info("accessToken 생성 - userId: {}, role: {}", userId, role);

        return jwtBuilder(userId, Token.ACCESS, properties.getAccessTokenExpireMs())
                .claim("role", role)
                .compact();
    }

    // refreshToken 생성 -> refreshToken 은 role 이 필요없음
    public String createRefreshToken(Long userId) {
        return jwtBuilder(userId, Token.REFRESH, properties.getRefreshTokenExpireMs())
                .compact();
    }

    // 토큰 공통 부분 생성
    private JwtBuilder jwtBuilder(Long userId, Token type, long expirationTime) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expirationTime);

        return Jwts.builder()
                .subject(String.valueOf(userId))
                .claim("type", type.name())
                .issuedAt(now)
                .expiration(expiration)
                .signWith(secretKey);
    }

    // 토큰 파싱
    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // 파싱된 토큰 검증
    public boolean validateToken(String token) {
        try {
            log.info("검증할 토큰: {}", token);
            parseClaims(token);

            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.warn("Invalid jwt token: {}", e.getMessage());
            return false;
        }
    }

    // 토큰에서 userId 추출
    public Long getUserId(String token) {
        return Long.valueOf(parseClaims(token).getSubject());
    }

    // 토큰에서 role 추출
    public String getRole(String token) {
        return parseClaims(token).get("role", String.class);
    }

    // 토큰에서 token 타입 추출
    public Token getTokenType(String token) {
        return Token.valueOf(parseClaims(token).get("type", String.class));
    }

    // 헤더나 쿠키 내부에 존재하는 refreshToken 추출
    public String extractRefreshToken(HttpServletRequest request) {
        // 헤더
        log.info("헤더에서 refreshToken 추출");
        String token = request.getHeader(Token.REFRESH.getKey());
        if (token != null && !token.isBlank()) {
            return token;
        }

        log.info("쿠키에서 refreshToken 추출");
        // 쿠키
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (Token.REFRESH.getKey().equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }

    // refreshToken 검증
    public void validateRefreshToken(String token) {
        try {
            Claims claims = parseClaims(token);

            if (!Token.REFRESH.name().equals(claims.get("type"))) {
                throw new JwtException("Not a refresh token");
            }
        } catch (JwtException | IllegalArgumentException e) {
            throw new RuntimeException("Invalid refresh token", e);
        }
    }
}
