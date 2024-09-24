package com.boltomart.vendor_service.response;

import com.boltomart.vendor_service.enums.AddressType;
import lombok.Data;

@Data
public class AddressResponse {

    private Long id;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private Integer pincode;
    private String state;
    private String phoneNumber;
    private AddressType addressType;
    private String latitude;
    private String longitude;
    private Integer status;
}
