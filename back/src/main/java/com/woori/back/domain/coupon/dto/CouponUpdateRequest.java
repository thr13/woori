package com.woori.back.domain.coupon.dto;

import com.woori.back.domain.coupon.entity.CouponStatus;
import lombok.Getter;

@Getter
public class CouponUpdateRequest {
    private CouponStatus couponStatus;
}
