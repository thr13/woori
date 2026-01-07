package com.woori.back.domain.cafe.entity;

import lombok.Getter;

@Getter
public enum CafeStatus {
    READY("준비 중"),
    OPEN("영업 중"),
    CLOSED("영업 종료"),
    REST("휴무");

    private final String value;

    CafeStatus(String value) {
        this.value = value;
    }
}
