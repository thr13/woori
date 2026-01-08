package com.woori.back.domain.stamp.dto;

import lombok.Getter;

@Getter
public class StampAccumulationRequest {
    private Long cafeId;
    private Long memberId;
    private int amount; // 적립할 스탬프 수
}
