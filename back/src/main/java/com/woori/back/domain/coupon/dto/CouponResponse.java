package com.woori.back.domain.coupon.dto;

import com.woori.back.domain.coupon.entity.Coupon;
import com.woori.back.domain.coupon.entity.CouponStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CouponResponse {
    private Long id; // 쿠폰 식별번호
    private CouponStatus couponStatus; // 쿠폰 상태

    public static CouponResponse from(Coupon coupon) {
        return new CouponResponse(coupon.getId(), coupon.getCouponStatus());
    }
}
