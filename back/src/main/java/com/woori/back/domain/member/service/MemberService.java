package com.woori.back.domain.member.service;

import com.woori.back.domain.member.dto.MemberSignUpRequest;
import com.woori.back.domain.member.entity.Member;
import com.woori.back.domain.member.entity.Role;
import com.woori.back.domain.member.exception.DuplicateEmailException;
import com.woori.back.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long signUp(MemberSignUpRequest request) {
        validateEmail(request.getEmail());

        String encodedPassword = encodePassword(request.getPassword());

        Member member = request.toEntity(encodedPassword, Role.CUSTOMER);

        return memberRepository.save(member).getId();
    }

    @Transactional(readOnly = true)
    public void validateEmail(String email) {
        log.info("이메일 검증 전: {}", email);

        if (memberRepository.existsByEmail(email)) {
            throw new DuplicateEmailException("Email already exists: " + email);
        }
    }

    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
