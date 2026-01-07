package com.woori.back.domain.coupon.entity;

import lombok.Getter;

@Getter
public enum CouponStatus {
    ACTIVE("사용가능"),
    USED("사용완료"),
    EXPIRED("만료")
    ;

    private final String value;

    CouponStatus(String value) {
        this.value = value;
    }
}
