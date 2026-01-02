package com.woori.back.global.filter;

import com.woori.back.global.jwt.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    // 헤더에서 accessToken 추출
    private String extractToken(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");

        if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) {
            return bearer.split(" ")[1];
        }

        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = extractToken(request);

        if (StringUtils.hasText(accessToken) && jwtProvider.validateToken(accessToken)) {

            Long userId = jwtProvider.getUserId(accessToken);
            String role = jwtProvider.getRole(accessToken);

            List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(role));
            Authentication authentication = new UsernamePasswordAuthenticationToken(userId, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            log.debug("인증 객체 저장 완료: UserId={}", userId);
        }

        filterChain.doFilter(request, response);
    }
}
