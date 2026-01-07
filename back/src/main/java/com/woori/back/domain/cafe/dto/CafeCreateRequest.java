package com.woori.back.domain.cafe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CafeCreateRequest {
    private String name;
    private String businessRegistrationNo;
    private String introduction;
    private String phone;
    private String imageUrl;
    private AddressDTO addressDTO;
}
