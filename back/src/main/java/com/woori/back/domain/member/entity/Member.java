package com.woori.back.domain.member.entity;

import com.woori.back.domain.cafe.entity.Cafe;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Entity
@Table(name = "member", uniqueConstraints = {@UniqueConstraint(columnNames = {"social_provider", "provider_id"})})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 회원 식별번호

    @Column(nullable = false)
    private String email; // 로그인 아이디 역할 겸 이메일

    @Column(nullable = false)
    private String password; // 비밀번호

    @Column(nullable = false)
    private String name; // 회원명

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role; // 회원 등급(손님, 사장, 관리자)

    @Enumerated(EnumType.STRING)
    @Column(name = "social_provider", nullable = true)
    private SocialProvider socialProvider; // 소셜 로그인 타입 (네이버, 깃허브, 구글 등)

    @Column(name = "provider_id", nullable = true)
    private String providerId; // oauth2 고유 식별자

    @OneToMany(mappedBy = "member")
    private List<Cafe> cafes = new ArrayList<>();

    protected Member(
            String email,
            String password,
            String name,
            Role role,
            SocialProvider socialProvider,
            String providerId
    ) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
        this.socialProvider = socialProvider;
        this.providerId = providerId;
    }

    public static Member createMember(
            String email,
            String password,
            String name,
            Role role
    ) {
        return new Member(
                email,
                password,
                name,
                role,
                SocialProvider.LOCAL,
                null
        );
    }

    public static Member createSocialMember(
            String email,
            String name,
            SocialProvider socialProvider,
            String providerId
    ) {
        return new Member(
                email,
                UUID.randomUUID().toString(),
                name,
                Role.CUSTOMER,
                socialProvider,
                providerId
        );
    }

    public void updateName(String name) {
        this.name = name;
    }
}
