package com.woori.back.global.oauth2;

import com.woori.back.domain.member.entity.Member;
import com.woori.back.domain.member.entity.SocialProvider;
import com.woori.back.global.oauth2.dto.KakaoAccount;
import com.woori.back.global.oauth2.dto.KakaoProfile;
import com.woori.back.global.oauth2.dto.KakaoResponseDTO;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class KakaoUserInfo {

    private final KakaoResponseDTO kaKaoResponseDTO;

    public String getProviderId() {
        return String.valueOf(kaKaoResponseDTO.getId());
    }

    public String getProvider() {
        return SocialProvider.KAKAO.name();
    }

    public String getEmail() {
        return Optional.ofNullable(kaKaoResponseDTO)
                .map(KakaoResponseDTO::getKakaoAccount)
                .map(KakaoAccount::getEmail)
                .orElse(null);
    }

    public String getNickName() {
        return Optional.ofNullable(kaKaoResponseDTO)
                .map(KakaoResponseDTO::getKakaoAccount)
                .map(KakaoAccount::getProfile)
                .map(KakaoProfile::getNickname)
                .orElse(null);
    }

    private KakaoAccount getAccount() {
        return kaKaoResponseDTO.getKakaoAccount();
    }

    public Member getKakaoMember() {
        return Member.createSocialMember(
                kaKaoResponseDTO.getKakaoAccount().getEmail(),
                kaKaoResponseDTO.getKakaoAccount().getProfile().getNickname(),
                SocialProvider.KAKAO,
                String.valueOf(kaKaoResponseDTO.getId())
        );
    }
}
