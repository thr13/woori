package com.woori.back.domain.auth.controller;

import com.woori.back.domain.auth.dto.LoginRequest;
import com.woori.back.domain.auth.dto.TokenResponse;
import com.woori.back.domain.member.dto.MemberSignUpRequest;
import com.woori.back.domain.member.dto.MemberSignUpResponse;
import com.woori.back.domain.auth.service.AuthService;
import com.woori.back.global.jwt.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtProvider jwtProvider;

    @PostMapping("/signup")
    public ResponseEntity<MemberSignUpResponse> signUp(@RequestBody MemberSignUpRequest request) {
        Long memberId = authService.signUp(request);
        log.info("생성된 회원 식별번호: {}", memberId);
        String email = request.getEmail();

        MemberSignUpResponse response = new MemberSignUpResponse(memberId, email);

        URI location = URI.create("/api/members/" + memberId);

        return ResponseEntity.created(location).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest request) {
        TokenResponse response = authService.login(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenResponse> reissue(HttpServletRequest request) {
        String refreshToken = jwtProvider.extractRefreshToken(request);

        if (refreshToken == null) {
            return ResponseEntity.badRequest().build();
        }

        TokenResponse response = authService.reissue(refreshToken);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        String refreshToken = jwtProvider.extractRefreshToken(request);

        if (refreshToken == null) {
            return ResponseEntity.badRequest().build();
        }

        authService.logout(refreshToken);

        return ResponseEntity.noContent().build();
    }
}
