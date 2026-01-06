package com.woori.back.domain.auth.entity;

import lombok.Getter;

@Getter
public enum Token {
    ACCESS("Authorization"),
    REFRESH("Refresh-Token"),
    ;

    private final String key;

    Token(String key) {
        this.key = key;
    }
}
