package com.woori.back.global.oauth2;

import com.woori.back.domain.member.entity.Member;
import com.woori.back.domain.member.entity.SocialProvider;
import com.woori.back.global.oauth2.dto.NaverResponse;
import com.woori.back.global.oauth2.dto.NaverResponseDTO;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NaverUserInfo {

    private final NaverResponseDTO naverResponseDTO;

    public String getProvider() {
        return SocialProvider.NAVER.name();
    }

    public String getProviderId() {
        return getResponse().getId();
    }

    public String getEmail() {
        return getResponse().getEmail();
    }

    public String getNickname() {
        return getResponse().getName();
    }

    private NaverResponse getResponse() {
        return naverResponseDTO.getResponse();
    }

    public Member getNaverMember() {
        return Member.createSocialMember(
                naverResponseDTO.getResponse().getEmail(),
                naverResponseDTO.getResponse().getName(),
                SocialProvider.NAVER,
                naverResponseDTO.getResponse().getId()
        );
    }
}
