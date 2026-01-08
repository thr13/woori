package com.woori.back.domain.stamp.dto;

import lombok.Getter;

@Getter
public class StampUseRequest {
    private Long cafeId;
    private Long memberId;
    private int amount; // 차감할 스탬프 수
}
