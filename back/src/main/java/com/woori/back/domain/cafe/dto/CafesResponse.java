package com.woori.back.domain.cafe.dto;

import com.woori.back.domain.cafe.entity.Cafe;
import com.woori.back.domain.cafe.entity.CafeStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CafesResponse {
    private Long cafeId;
    private String name;
    private String imageUrl;
    private CafeStatus cafeStatus;
    private AddressDTO address;

    public static CafesResponse from(Cafe cafe) {
        CafesResponse cafesResponse = new CafesResponse();
        cafesResponse.cafeId = cafe.getId();
        cafesResponse.name = cafe.getName();
        cafesResponse.imageUrl = cafe.getImageUrl();
        cafesResponse.cafeStatus = cafe.getCafeStatus();
        cafesResponse.address = AddressDTO.from(cafe.getAddress());

        return cafesResponse;
    }
}
