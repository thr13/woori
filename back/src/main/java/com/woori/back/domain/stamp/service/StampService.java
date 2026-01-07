package com.woori.back.domain.stamp.service;

import com.woori.back.domain.cafe.entity.Cafe;
import com.woori.back.domain.cafe.exception.NotFoundCafeException;
import com.woori.back.domain.cafe.repository.CafeRepository;
import com.woori.back.domain.member.entity.Member;
import com.woori.back.domain.member.repository.MemberRepository;
import com.woori.back.domain.stamp.dto.StampCreateRequest;
import com.woori.back.domain.stamp.dto.StampCreateResponse;
import com.woori.back.domain.stamp.dto.StampResponse;
import com.woori.back.domain.stamp.entity.Stamp;
import com.woori.back.domain.stamp.exception.NotFoundException;
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
        Cafe cafe = getCafeById(request.getCafeId());
        Member member = getMemberById(request.getMemberId());

        Stamp stamp = Stamp.builder()
                .member(member)
                .cafe(cafe)
                .amount(0)
                .total(0)
                .build();

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

    private Stamp getStampById(Long stampId) {
        return stampRepository.findById(stampId)
                .orElseThrow(
                        () -> new NotFoundException("Not found stamp by id " + stampId)
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
}
