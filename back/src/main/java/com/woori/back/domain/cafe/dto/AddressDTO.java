package com.woori.back.domain.cafe.dto;

import com.woori.back.domain.cafe.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
    private String zipcode;
    private String city;
    private String street;
    private String detail;

    public Address to() {
        return new Address(zipcode, city, street, detail);
    }

    public static AddressDTO from(Address address) {
        return new AddressDTO(
                address.getZipcode(),
                address.getCity(),
                address.getStreet(),
                address.getDetail()
        );
    }
}
