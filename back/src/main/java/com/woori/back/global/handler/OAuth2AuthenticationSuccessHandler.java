package com.woori.back.global.handler;

import com.woori.back.domain.auth.entity.RefreshToken;
import com.woori.back.domain.auth.repository.RefreshTokenRepository;
import com.woori.back.global.jwt.JwtProvider;
import com.woori.back.global.oauth2.SocialUser;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProvider jwtProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        SocialUser socialUser = (SocialUser) authentication.getPrincipal(); // SocialUserService 에서 반환한 SocialUser 추출
        Long memberId = socialUser.getMember().getId();

        log.info("oauth2 login 성공");
        String refreshToken = jwtProvider.createRefreshToken(memberId); // todo: 액세스 토큰은 프론트에서 별도로 api 컨트롤러 호출
        log.info("jwt token 발급 완료: memberId = {}", memberId);

        RefreshToken saveRefreshToken = new RefreshToken(UUID.randomUUID().toString(), refreshToken, memberId);
        refreshTokenRepository.deleteByMemberId(memberId); // 기존 리프레쉬 토큰 삭제
        refreshTokenRepository.save(saveRefreshToken); // 새 리프레쉬 토큰 저장

        ResponseCookie cookie = ResponseCookie.from("Refresh-Token", refreshToken)
                .httpOnly(true)
                .secure(false) // false: http, https 둘다 가능, ture: https 만 가능
                .path("/")
                .sameSite("Lax") // Todo: Secure(ture) 시 SameSite 전략을 None 으로 변경할 것
                .maxAge(60 * 60 * 7 * 24) // 쿠키 수명 == 리프레쉬 토큰 수명
                .build();

        String redirectURI = "http://localhost:5173/oauth2/redirect";

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        response.sendRedirect(redirectURI);
    }
}
