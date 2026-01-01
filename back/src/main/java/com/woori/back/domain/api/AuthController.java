package com.woori.back.domain.api;

import com.woori.back.domain.member.dto.MemberSignUpRequest;
import com.woori.back.domain.member.dto.MemberSignUpResponse;
import com.woori.back.domain.member.service.MemberService;
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

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<MemberSignUpResponse> signUp(@RequestBody MemberSignUpRequest request) {

        Long memberId = memberService.signUp(request);
        log.info("생성된 회원 식별번호: {}", memberId);
        String email = request.getEmail();

        MemberSignUpResponse response = new MemberSignUpResponse(memberId, email);

        URI location = URI.create("/api/members/" + memberId);

        return ResponseEntity.created(location).body(response);
    }
}
