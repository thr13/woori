package com.woori.back.global.oauth2.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NaverResponse {
    private String id;
    private String nickname; // 닉네임
    private String name; // 회원이름
    private String email; // 이메일
    private String birthday; // 생일
    private String mobile; // 휴대전화번호
}
