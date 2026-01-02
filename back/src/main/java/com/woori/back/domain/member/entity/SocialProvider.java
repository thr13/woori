package com.woori.back.domain.member.entity;

import lombok.Getter;

@Getter
public enum SocialProvider {
    GITHUB("깃허브"),
    GOOGLE("구글"),
    KAKAO("카카오"),
    NAVER("네이버"),
    ;

    private final String value;

    SocialProvider(String value) {
        this.value = value;
    }
}
