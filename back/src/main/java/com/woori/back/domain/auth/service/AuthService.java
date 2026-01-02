package com.woori.back.domain.auth.service;

import com.woori.back.domain.auth.dto.LoginRequest;
import com.woori.back.domain.auth.dto.TokenResponse;
import com.woori.back.domain.auth.entity.RefreshToken;
import com.woori.back.domain.auth.exception.NotFoundRefreshTokenException;
import com.woori.back.domain.auth.repository.RefreshTokenRepository;
import com.woori.back.domain.member.dto.MemberSignUpRequest;
import com.woori.back.domain.member.entity.Member;
import com.woori.back.domain.member.entity.Role;
import com.woori.back.domain.member.exception.DuplicateEmailException;
import com.woori.back.domain.member.exception.NotFoundMemberException;
import com.woori.back.domain.member.repository.MemberRepository;
import com.woori.back.global.jwt.JwtProperties;
import com.woori.back.global.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final JwtProperties jwtProperties;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public Long signUp(MemberSignUpRequest request) {
        validateEmail(request.getEmail());

        String encodedPassword = encodePassword(request.getPassword());

        Member member = request.toEntity(encodedPassword, Role.CUSTOMER);

        return memberRepository.save(member).getId();
    }

    @Transactional(readOnly = true)
    public void validateEmail(String email) {
        log.info("이메일 검증 전: {}", email);

        if (memberRepository.existsByEmail(email)) {
            throw new DuplicateEmailException("Email already exists: " + email);
        }
    }

    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Transactional
    public TokenResponse login(LoginRequest request) {

        Member member = findByEmail(request.getEmail());

        String accessToken = jwtProvider.createAccessToken(member.getId(), member.getRole().name());
        String refreshToken = jwtProvider.createRefreshToken(member.getId());

        RefreshToken newRefreshToken = new RefreshToken(UUID.randomUUID().toString(), refreshToken, member.getId());

        refreshTokenRepository.save(newRefreshToken);

        return new TokenResponse(accessToken, refreshToken, jwtProperties.getAccessTokenExpireMs());
    }

    private Member findByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(
                        () -> new NotFoundMemberException("Not found member by email: " + email)
                );
    }

    @Transactional
    public TokenResponse reissue(String refreshToken) {
        log.info("토큰 재발급 전: {}", refreshToken);

        jwtProvider.validateRefreshToken(refreshToken);

        Long userId = jwtProvider.getUserId(refreshToken);
        String role = jwtProvider.getRole(refreshToken);

        refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(
                        () -> new NotFoundRefreshTokenException("Invalid token")
                );

        String newAccessToken = jwtProvider.createAccessToken(userId, role);
        log.info("재발급된 accessToken: {}", newAccessToken);

        return new TokenResponse(newAccessToken, refreshToken, jwtProperties.getRefreshTokenExpireMs());
    }
}
