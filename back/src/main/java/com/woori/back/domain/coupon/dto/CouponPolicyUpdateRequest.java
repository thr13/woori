package com.woori.back.domain.coupon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CouponPolicyUpdateRequest {
    private String reward;
    private int stampNum;
}
