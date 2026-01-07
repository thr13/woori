package com.woori.back.domain.cafe.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Address {

    @Column(length = 6)
    private String zipcode; // 우편번호

    private String city; // 시(도)

    private String street; // 도로명

    private String detail; // 상세주소
}