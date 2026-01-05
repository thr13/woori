package com.woori.back.global.oauth2.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class NaverResponseDTO {
    @JsonProperty("resultcode")
    private String resultCode; // 공식 명세서는 resultcode
    private String message;
    private NaverResponse response;
}
