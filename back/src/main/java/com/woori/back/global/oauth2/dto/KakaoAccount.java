package com.woori.back.global.oauth2.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class KakaoAccount {
    private String email;

    @JsonProperty("profile")
    private KakaoProfile profile;
}
