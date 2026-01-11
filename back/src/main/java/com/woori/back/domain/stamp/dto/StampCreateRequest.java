package com.woori.back.domain.stamp.dto;

import lombok.Getter;

@Getter
public class StampCreateRequest {
    private Long memberId; // 스탬프를 받는 손님
    private Long cafeId; // 적립할 카페
    private int amount; // 찍을 스탬프 수
}
