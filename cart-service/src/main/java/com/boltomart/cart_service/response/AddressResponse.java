package com.boltomart.cart_service.response;

import com.boltomart.cart_service.enums.AddressType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class AddressResponse {

    private Long id;
    private Long customerId; // To link it with Customer
    private String addressLine1;
    private String addressLine2;
    private String city;
    private Integer pincode;
    private String state;
    private String phoneNumber;
    private AddressType addressType;
    private Integer status;

}