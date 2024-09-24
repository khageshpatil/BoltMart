package com.boltomart.vendor_service.response;

import com.boltomart.vendor_service.enums.AddressType;

import lombok.Data;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class VendorResponse {

    private Long id;
    private String vendorName;
    private String logo;
    private String shopName;
    private String gstNumber;
    private String phoneNumber;
    private AddressResponse address;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
