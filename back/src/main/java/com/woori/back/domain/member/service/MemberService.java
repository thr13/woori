package com.woori.back.domain.member.service;

import com.woori.back.domain.member.dto.MemberResponse;
import com.woori.back.domain.member.entity.Member;
import com.woori.back.domain.member.exception.NotFoundMemberException;
import com.woori.back.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberResponse getMe(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new NotFoundMemberException("Not found member: " + memberId));

        return MemberResponse.from(member);
    }
}