package com.woori.back.domain.member.controller;

import com.woori.back.domain.member.dto.MemberResponse;
import com.woori.back.domain.member.service.MemberService;
import com.woori.back.global.oauth2.SocialUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/me")
    public ResponseEntity<MemberResponse> me(@AuthenticationPrincipal SocialUser socialUser) {
        Long memberId = socialUser.getMember().getId();

        MemberResponse response = memberService.getMe(memberId);

        return ResponseEntity.ok(response);
    }
}
