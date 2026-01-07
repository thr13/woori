package com.woori.back.domain.cafe.service;

import com.woori.back.domain.cafe.dto.*;
import com.woori.back.domain.cafe.entity.Address;
import com.woori.back.domain.cafe.entity.Cafe;
import com.woori.back.domain.cafe.exception.NotFoundCafeException;
import com.woori.back.domain.cafe.repository.CafeRepository;
import com.woori.back.domain.member.entity.Member;
import com.woori.back.domain.member.exception.NotFoundMemberException;
import com.woori.back.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
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
        log.info("카페 생성 성공: cafeId={}", cafe.getId());

        return new CafeCreateResponse(cafe.getId(), cafe.getName());
    }

    @Transactional(readOnly = true)
    public CafeResponse getCafeInfo(Long cafeId) {
        Cafe cafe = findCafeById(cafeId);
        log.info("카페 조회(단일): {}", cafe.toString());

        return CafeResponse.from(cafe);
    }

    @Transactional(readOnly = true)
    public Page<CafesResponse> getMyCafes(Long memberId, Pageable pageable) {
        log.info("내 카페 목록 조회: {}", memberId);

        return cafeRepository.findByMember_Id(memberId, pageable);
    }

    @Transactional(readOnly = true)
    public Page<CafesResponse> getCafes(Pageable pageable) {
        log.info("전체 카페 목록 조회");

        return cafeRepository.findAll(pageable)
                .map(CafesResponse::from);
    }

    @Transactional
    public CafeResponse updateCafeInfo(Long cafeId, CafeUpdateRequest request) {
        log.info("카페 정보 업데이트");
        Cafe cafe = findCafeById(cafeId);
        cafe.updateInfo(
                request.getName(),
                request.getIntroduction(),
                request.getPhone(),
                request.getImageUrl(),
                request.getAddress().to()
        );

        return CafeResponse.from(cafe);
    }

    @Transactional
    public void deleteCafe(Long cafeId) {
        log.info("카페 삭제");
        cafeRepository.deleteById(cafeId);
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(
                        () -> new NotFoundMemberException("Not found member by id: " + memberId)
                );
    }

    private Cafe findCafeById(Long cafeId) {
        return cafeRepository.findById(cafeId)
                .orElseThrow(
                        () -> new NotFoundCafeException("Not found Cafe by id: " + cafeId)
                );
    }
}
