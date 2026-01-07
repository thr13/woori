package com.woori.back.domain.cafe.dto;

import com.woori.back.domain.cafe.entity.Cafe;
import com.woori.back.domain.cafe.entity.CafeStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CafeResponse {
    private Long cafeId;
    private String name;
    private String businessRegistrationNo;
    private String introduction;
    private String phone;
    private String imageUrl;
    private CafeStatus cafeStatus;
    private AddressDTO address;

    public static CafeResponse from(Cafe cafe) {
        CafeResponse cafeResponse = new CafeResponse();
        cafeResponse.cafeId = cafe.getId();
        cafeResponse.name = cafe.getName();
        cafeResponse.businessRegistrationNo = cafe.getBusinessRegistrationNo();
        cafeResponse.introduction = cafe.getIntroduction();
        cafeResponse.phone = cafe.getPhone();
        cafeResponse.imageUrl = cafe.getImageUrl();
        cafeResponse.cafeStatus = cafe.getCafeStatus();
        cafeResponse.address = AddressDTO.from(cafe.getAddress());

        return cafeResponse;
    }
}
