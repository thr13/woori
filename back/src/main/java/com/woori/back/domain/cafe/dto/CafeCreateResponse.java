package com.woori.back.domain.cafe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CafeCreateResponse {
    private Long id; // 카페 식별번호
    private String name; // 카페 주인
}
