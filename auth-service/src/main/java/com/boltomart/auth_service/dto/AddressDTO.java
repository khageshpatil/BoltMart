package com.boltomart.auth_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class AddressDTO {

    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private Integer pinCode;
    private String country;

}
