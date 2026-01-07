package com.woori.back.domain.cafe.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CafeUpdateRequest {
    private String name;
    private String introduction;
    private String phone;
    private String imageUrl;
    private AddressDTO address;
}
