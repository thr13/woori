package com.woori.back.domain.member.entity;

import lombok.Getter;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;

import java.util.Arrays;

@Getter
public enum SocialProvider {
    LOCAL("local"),
    GITHUB("github"),
    GOOGLE("google"),
    KAKAO("kakao"),
    NAVER("naver"),
    ;

    private final String value;

    SocialProvider(String value) {
        this.value = value;
    }

    public static SocialProvider getSocialProvider(String registrationId) {
        return Arrays.stream(values())
                .filter(socialProvider -> socialProvider.value.equals(registrationId))
                .findFirst()
                .orElseThrow(() ->
                        new OAuth2AuthenticationException("지원하지 않는 provider 입니다: " + registrationId)
                );
    }
}
