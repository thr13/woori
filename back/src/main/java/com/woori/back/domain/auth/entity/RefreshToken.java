package com.woori.back.domain.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@AllArgsConstructor
@RedisHash(value = "refreshToken", timeToLive = 60 * 60 * 24 * 7) // 유효기간 7일
public class RefreshToken {
    @Id
    private String id; // 토큰 식별키
    @Indexed
    private String token; // 토큰명
    private Long memberId; // 회원 식별키
}
