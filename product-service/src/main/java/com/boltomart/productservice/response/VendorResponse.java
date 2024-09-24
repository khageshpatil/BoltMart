package com.boltomart.productservice.response;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VendorResponse {
    private Long id;
    private String vendorName;
    private String logo;
    private String shopName;
    private String gstNumber;
    private String phoneNumber;
//  private AddressResponse address;
   
//  private List<ProductResponse> products;
//  private List<SessionResponse> sessions;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}