package com.woori.back.domain.coupon.service;

import com.woori.back.domain.cafe.entity.Cafe;
import com.woori.back.domain.cafe.exception.NotFoundCafeException;
import com.woori.back.domain.cafe.repository.CafeRepository;
import com.woori.back.domain.coupon.dto.CouponPolicyCreateRequest;
import com.woori.back.domain.coupon.dto.CouponPolicyCreateResponse;
import com.woori.back.domain.coupon.dto.CouponPolicyResponse;
import com.woori.back.domain.coupon.dto.CouponPolicyUpdateRequest;
import com.woori.back.domain.coupon.entity.CouponPolicy;
import com.woori.back.domain.coupon.exception.NotFoundCouponPolicyException;
import com.woori.back.domain.coupon.repository.CouponPolicyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CouponPolicyService {

    private final CouponPolicyRepository couponPolicyRepository;
    private final CafeRepository cafeRepository;

    // 정책 생성
    @Transactional
    public CouponPolicyCreateResponse createCouponPolicy(CouponPolicyCreateRequest request) {
        log.info("쿠폰정책 생성");
        Long cafeId = request.getCafeId();
        String reward = request.getReward();
        int stampNum = request.getStampNum();

        Cafe cafe = findCafeByCafeId(cafeId);

        CouponPolicy couponPolicy = CouponPolicy.builder()
                .cafe(cafe)
                .reward(reward)
                .stampNum(stampNum)
                .build();

        return CouponPolicyCreateResponse.from(couponPolicy.getId(), cafe.getName());
    }

    // 정책 조회(단일)
    @Transactional(readOnly = true)
    public CouponPolicyResponse getCouponPolicyInfo(Long couponPolicyId) {
        log.info("쿠폰 정책 조회: {}", couponPolicyId);
        CouponPolicy couponPolicy = findCouponPolicyById(couponPolicyId);

        return CouponPolicyResponse.from(couponPolicy);
    }

    // 정책 목록 조회
    @Transactional(readOnly = true)
    public Page<CouponPolicyResponse> getCouponPolicies(Pageable pageable) {
        log.info("쿠폰 정책 목록 조회");

        return couponPolicyRepository.findAll(pageable)
                .map(CouponPolicyResponse::from);
    }

    // 정책 수정 (정보수정)
    @Transactional
    public CouponPolicyResponse updateCouponPolicyInfo(Long couponPolicyId, CouponPolicyUpdateRequest request) {
        log.info("쿠폰 정책 수정: {}", couponPolicyId);
        CouponPolicy couponPolicy = findCouponPolicyById(couponPolicyId);
        couponPolicy.update(request.getReward(), request.getStampNum());

        return CouponPolicyResponse.from(couponPolicy);
    }

    // 정책 삭제
    @Transactional
    public void deleteCouponPolicy(Long couponPolicyId) {
        log.info("쿠폰 정책 삭제: {}", couponPolicyId);
        CouponPolicy couponPolicy = findCouponPolicyById(couponPolicyId);

        couponPolicyRepository.delete(couponPolicy);
    }

    private Cafe findCafeByCafeId(Long cafeId) {
        return cafeRepository.findById(cafeId)
                .orElseThrow(
                        () -> new NotFoundCafeException("Not found cafe by id: " + cafeId)
                );
    }

    private CouponPolicy findCouponPolicyById(Long couponPolicyId) {
        return couponPolicyRepository.findById(couponPolicyId)
                .orElseThrow(
                        () -> new NotFoundCouponPolicyException("Not found coupon policy by id: " + couponPolicyId)
                );
    }
}
