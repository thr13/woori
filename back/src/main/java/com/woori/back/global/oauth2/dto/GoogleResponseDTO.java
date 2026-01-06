package com.woori.back.global.oauth2.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class GoogleResponseDTO {
    private String sub; // 구글의 providerId
    private String email; // 이메일
    private String name; // 전체 이름
    @JsonProperty("given_name")
    private String givenName; // 이름
    @JsonProperty("family_name")
    private String familyName; // 성
}
