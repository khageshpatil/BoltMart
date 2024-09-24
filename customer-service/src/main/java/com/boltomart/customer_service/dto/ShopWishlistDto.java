package com.boltomart.customer_service.dto;

import lombok.Data;

@Data
public class ShopWishlistDto {
    private Long id;
    private Long customerId;
    private Long vendorId;
}
