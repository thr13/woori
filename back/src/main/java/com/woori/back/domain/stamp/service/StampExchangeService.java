package com.woori.back.domain.stamp.service;

import com.woori.back.domain.coupon.entity.Coupon;
import com.woori.back.domain.coupon.entity.CouponPolicy;
import com.woori.back.domain.coupon.service.CouponPolicyService;
import com.woori.back.domain.coupon.service.CouponService;
import com.woori.back.domain.stamp.entity.Stamp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class StampExchangeService {

    private final StampService stampService;
    private final CouponService couponService;
    private final CouponPolicyService couponPolicyService;

    // 스탬프 교환 -> 쿠폰 발급
    @Transactional
    public Coupon exchange(Long memberId, Long cafeId) {
        log.info("스탬프 쿠폰 교환");
        Stamp stamp = stampService.getStamp(memberId, cafeId);

        CouponPolicy couponPolicy = couponPolicyService.getCouponPolicy(cafeId);
        int stampNum = couponPolicy.getStampNum(); // 쿠폰 정책에 표기된 소모될 스탬프 수

        stamp.use(stampNum); // 스탬프 사용

        return couponService.issueCoupon(memberId, cafeId, couponPolicy); // 쿠폰 발급
    }
}
