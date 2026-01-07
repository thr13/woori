package com.woori.back.domain.coupon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CouponPolicyCreateRequest {
    private Long cafeId;
    private String reward;
    private int stampNum;
}
