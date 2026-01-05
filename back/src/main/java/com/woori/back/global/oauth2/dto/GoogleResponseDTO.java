package com.woori.back.global.oauth2.dto;

import lombok.Getter;

@Getter
public class GoogleResponseDTO {
    private String sub; // 구글의 providerId
    private String profile;
    private String email;
}
