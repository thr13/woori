package com.woori.back.domain.stamp.service;

import com.woori.back.domain.cafe.entity.Cafe;
import com.woori.back.domain.cafe.exception.NotFoundCafeException;
import com.woori.back.domain.cafe.repository.CafeRepository;
import com.woori.back.domain.member.entity.Member;
import com.woori.back.domain.member.repository.MemberRepository;
import com.woori.back.domain.stamp.dto.*;
import com.woori.back.domain.stamp.entity.Stamp;
import com.woori.back.domain.stamp.exception.NotCafeOwnerException;
import com.woori.back.domain.stamp.exception.NotFoundStampException;
import com.woori.back.domain.stamp.repository.StampRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class StampService {

    private final StampRepository stampRepository;
    private final CafeRepository cafeRepository;
    private final MemberRepository memberRepository;

    // 스탬프 생성
    @Transactional
    public StampCreateResponse createStamp(StampCreateRequest request) {
        log.info("스탬프 생성");
        Long cafeId = request.getCafeId();
        Long memberId = request.getMemberId();

        Cafe cafe = getCafeById(cafeId);
        Member member = getMemberById(memberId);

        validateOwner(cafeId, memberId); // 카페 주인 검증

        int amount = request.getAmount();

        Stamp stamp = Stamp.builder()
                .member(member)
                .cafe(cafe)
                .amount(amount)
                .total(amount)
                .build();

        stampRepository.save(stamp); // 스탬프 저장

        return StampCreateResponse.from(stamp);
    }

    // 스탬프 조회
    @Transactional(readOnly = true)
    public StampResponse getStampInfo(Long stampId) {
        log.info("스탬프 조회");
        Stamp stamp = getStampById(stampId);

        return StampResponse.from(stamp);
    }

    // 스탬프 목록 조회
    @Transactional(readOnly = true)
    public Page<StampResponse> getStamps(Pageable pageable) {
        log.info("스탬프 목록 조회");
        return stampRepository.findAll(pageable)
                .map(StampResponse::from);
    }

    // 스탬프 제거
    @Transactional
    public void deleteStamp(Long stampId) {
        log.info("스탬프 제거");
        Stamp stamp = getStampById(stampId);

        stampRepository.delete(stamp);
    }

    // 스탬프 적립
    @Transactional
    public StampResponse accumulationStamp(Long stampId, StampAccumulationRequest request) {
        log.info("스탬프 적립");
        Long cafeId = request.getCafeId();
        Long memberId = request.getMemberId();

        validateOwner(cafeId, memberId);

        Stamp stamp = getStampById(stampId);

        int amount = request.getAmount();
        stamp.accumulation(amount);

        // todo: db 반영 -> 더티체킹?

        return StampResponse.from(stamp);
    }

    // 스탬프 사용
    @Transactional
    public StampResponse useStamp(Long stampId, StampUseRequest request) {
        log.info("스탬프 사용(스탬프 서비스)");
        Stamp stamp = getStampById(stampId);

        int amount = request.getAmount();
        stamp.use(amount);

        // todo: 스탬프로 교환할 쿠폰 로직 추가 필요

        return StampResponse.from(stamp);
    }

    private Stamp getStampById(Long stampId) {
        return stampRepository.findById(stampId)
                .orElseThrow(
                        () -> new NotFoundStampException("Not found stamp by id " + stampId)
                );
    }

    private Cafe getCafeById(Long cafeId) {
        return cafeRepository.findById(cafeId)
                .orElseThrow(
                        () -> new NotFoundCafeException("Not found cafe by id " + cafeId)
                );
    }

    private Member getMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(
                        () -> new NotFoundCafeException("Not found member by id " + memberId)
                );
    }

    private void validateOwner(Long cafeId, Long memberId) {
        if (!cafeId.equals(memberId)) {
            throw new NotCafeOwnerException("Only the owner of cafe can stamp");
        }
    }
}
