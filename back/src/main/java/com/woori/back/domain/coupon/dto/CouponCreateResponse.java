package com.woori.back.domain.coupon.dto;

import com.woori.back.domain.coupon.entity.Coupon;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CouponCreateResponse {
    private Long id; // 쿠폰 식별번호
    private String code; // 쿠폰 코드

    public static CouponCreateResponse from(Coupon coupon) {
        return new CouponCreateResponse(coupon.getId(), coupon.getCode());
    }
}
