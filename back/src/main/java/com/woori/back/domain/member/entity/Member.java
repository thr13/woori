package com.woori.back.domain.member.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 회원 식별번호

    @Column(nullable = false, unique = true)
    private String email; // 로그인 아이디 역할 겸 이메일

    @Column(nullable = false)
    private String password; // 비밀번호

    @Column(nullable = false)
    private String name; // 회원명

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role; // 회원 등급(손님, 사장, 관리자)

    @Column(nullable = true)
    private SocialProvider socialProvider; // 소셜 로그인 타입 (네이버, 깃허브, 구글 등)

    @Builder
    private Member(String email, String password, String name, Role role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
    }
}
