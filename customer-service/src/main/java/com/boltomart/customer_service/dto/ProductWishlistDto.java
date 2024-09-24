package com.boltomart.customer_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductWishlistDto {

    private Long id;
    private Long customerId;
    private Long productId;
}
