package com.woori.back.global.oauth2;

import com.woori.back.domain.member.entity.Member;
import com.woori.back.domain.member.entity.SocialProvider;
import com.woori.back.global.oauth2.dto.GoogleResponseDTO;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GoogleUserInfo {

    private final GoogleResponseDTO googleResponseDTO;

    public String getProviderId() {
        return googleResponseDTO.getSub();
    }

    public SocialProvider getProvider() {
        return SocialProvider.GOOGLE;
    }

    public String getEmail() {
        return googleResponseDTO.getEmail();
    }

    public String getProfile() {
        return googleResponseDTO.getProfile(); // profile -> name
    }

    public Member getGoogleMember() {
        return Member.createSocialMember(
                googleResponseDTO.getEmail(),
                googleResponseDTO.getProfile(),
                SocialProvider.GOOGLE,
                googleResponseDTO.getSub()
        );
    }
}
