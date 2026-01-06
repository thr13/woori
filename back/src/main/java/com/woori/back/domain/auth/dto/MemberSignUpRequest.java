package com.woori.back.domain.auth.dto;

import com.woori.back.domain.member.entity.Member;
import com.woori.back.domain.member.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberSignUpRequest {
    @Email @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String name;

    public Member signUp(String password, Role role) {
        return Member.createMember(
                this.email,
                password,
                this.name,
                role
        );
    }
}
