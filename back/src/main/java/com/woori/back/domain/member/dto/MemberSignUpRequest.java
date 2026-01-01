package com.woori.back.domain.member.dto;

import com.woori.back.domain.member.entity.Member;
import com.woori.back.domain.member.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberSignUpRequest {
    private String email;
    private String password;
    private String name;

    public Member toEntity(String password, Role role) {
        return Member.builder()
                .email(this.email)
                .password(password)
                .name(this.name)
                .role(role)
                .build();
    }
}
