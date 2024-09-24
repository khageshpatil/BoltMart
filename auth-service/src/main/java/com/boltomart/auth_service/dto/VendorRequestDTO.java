package com.boltomart.auth_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class VendorRequestDTO {
    private String vendorName;
    private String shopName;
    private String gstNumber;
    private String shopPhoneNumber;
    private String logo;
    private AddressDTO address;

    @JsonProperty("device")
    private String device;

    @JsonProperty("ipAddress")
    private String ipAddress;
    // Constructors, getters, and setters
}
