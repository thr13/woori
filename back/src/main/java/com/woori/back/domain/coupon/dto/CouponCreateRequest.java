package com.woori.back.domain.coupon.dto;

import com.woori.back.domain.coupon.entity.CouponStatus;
import lombok.Getter;

@Getter
public class CouponCreateRequest {
    private Long couponPolicyId;
    private Long memberId;
    private Long cafeId;
    private CouponStatus couponStatus;
}
