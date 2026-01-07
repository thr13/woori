package com.woori.back.domain.coupon.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.woori.back.domain.coupon.entity.CouponPolicy;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CouponPolicyResponse {

    @JsonProperty(value = "cafe_id")
    private Long cafeId;

    private String reward;

    @JsonProperty(value = "stamp_num")
    private int stampNum;

    public static CouponPolicyResponse from(CouponPolicy couponPolicy) {
        return new CouponPolicyResponse(
                couponPolicy.getCafe().getId(),
                couponPolicy.getReward(),
                couponPolicy.getStampNum()
        );
    }
}
