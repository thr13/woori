package com.woori.back.domain.coupon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CouponPolicyCreateResponse {
    private Long id; // 쿠폰정책 식벌변호
    private String name; // 카페명

    public static CouponPolicyCreateResponse from(Long couponPolicyId, String cafeName) {
        return new CouponPolicyCreateResponse(couponPolicyId, cafeName);
    }
}
