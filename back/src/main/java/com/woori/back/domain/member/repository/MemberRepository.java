package com.woori.back.domain.member.repository;

import com.woori.back.domain.member.entity.Member;
import com.woori.back.domain.member.entity.SocialProvider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByEmail(String email);

    Optional<Member> findByEmail(String email);

    Optional<Member> findBySocialProviderAndProviderId(SocialProvider socialProvider, String providerId);

}
