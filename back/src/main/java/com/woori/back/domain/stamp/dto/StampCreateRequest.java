package com.woori.back.domain.stamp.dto;

import lombok.Getter;

@Getter
public class StampCreateRequest {
    private Long memberId;
    private Long cafeId;
}
