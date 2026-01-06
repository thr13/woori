package com.woori.back.global.oauth2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woori.back.domain.member.entity.Member;
import com.woori.back.domain.member.entity.SocialProvider;
import com.woori.back.domain.member.repository.MemberRepository;
import com.woori.back.global.oauth2.dto.GoogleResponseDTO;
import com.woori.back.global.oauth2.dto.KakaoResponseDTO;
import com.woori.back.global.oauth2.dto.NaverResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SocialUserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;
    private final ObjectMapper objectMapper;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest); // 스프링 시큐리티 oauth2
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String registrationId = userRequest.getClientRegistration().getRegistrationId(); // provider 식별키
        log.info("attributes={}, registrationId={}", attributes, registrationId);
        SocialProvider socialProvider = SocialProvider.getSocialProvider(registrationId);

        Member member = extractSocialMember(socialProvider, attributes); // oauth2 타입별 Member 객체 생성
        Member socialMember = loginMember(member);
        return new SocialUser(socialMember, attributes);
    }

    public Member extractSocialMember(SocialProvider socialProvider, Map<String, Object> attributes) {
        if (socialProvider.equals(SocialProvider.GOOGLE)) {
            GoogleResponseDTO googleResponseDTO = objectMapper.convertValue(attributes, GoogleResponseDTO.class);

            return new GoogleUserInfo(googleResponseDTO).getGoogleMember();
        } else if (socialProvider.equals(SocialProvider.NAVER)) {
            NaverResponseDTO naverResponseDTO = objectMapper.convertValue(attributes, NaverResponseDTO.class);

            return new NaverUserInfo(naverResponseDTO).getNaverMember();
        } else if (socialProvider.equals(SocialProvider.KAKAO)) {
            KakaoResponseDTO kakaoResponseDTO = objectMapper.convertValue(attributes, KakaoResponseDTO.class);

            return new KakaoUserInfo(kakaoResponseDTO).getKakaoMember();
        } else {
            throw new OAuth2AuthenticationException("Unsupported provider type: " + socialProvider.name());
        }
    }

    public Member loginMember(Member member) {
        return memberRepository
                .findBySocialProviderAndProviderId(member.getSocialProvider(), member.getProviderId())
                .map(existing -> {
                    log.info("기존 회원 로그인: id = {}, provider = {}", existing.getId(), existing.getSocialProvider());
                    existing.updateName(member.getName());

                    return existing;
                })
                .orElseGet(() -> {
                    log.info("신규 회원 가입");

                    return memberRepository.save(member);
                });
    }

}
