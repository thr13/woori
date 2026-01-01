package com.woori.back.domain.member.entity;

import lombok.Getter;

@Getter
public enum Role {
    CUSTOMER("손님"),
    OWNER("사장"),
    ADMIN("관리자");

    private final String value;

    Role(String value) {
        this.value = value;
    }

    public String getPreRole() {
        return "ROLE_" + this.name();
    }
}
