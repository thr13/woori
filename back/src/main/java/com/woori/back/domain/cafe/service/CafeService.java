package com.woori.back.domain.cafe.service;

import com.woori.back.domain.cafe.dto.CafeCreateRequest;
import com.woori.back.domain.cafe.dto.CafeCreateResponse;
import com.woori.back.domain.cafe.entity.Address;
import com.woori.back.domain.cafe.entity.Cafe;
import com.woori.back.domain.cafe.repository.CafeRepository;
import com.woori.back.domain.member.entity.Member;
import com.woori.back.domain.member.exception.NotFoundMemberException;
import com.woori.back.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CafeService {

    private final CafeRepository cafeRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public CafeCreateResponse createCafe(Long memberId, CafeCreateRequest request) {
        Member member = findMemberById(memberId);

        Address address = request.getAddressDTO().to(); // 주소 객체 생성

        Cafe cafe = Cafe.builder()
                .member(member)
                .name(request.getName())
                .businessRegistrationNo(request.getBusinessRegistrationNo())
                .introduction(request.getIntroduction())
                .phone(request.getPhone())
                .imageUrl(request.getImageUrl())
                .address(address)
                .build(); // 카페 객체 생성

        cafeRepository.save(cafe); // db 에 저장

        return new CafeCreateResponse(cafe.getId(), cafe.getName());
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(
                        () -> new NotFoundMemberException("Not found member by id: " + memberId)
                );
    }
}
