package com.boltomart.customer_service.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
@Data @AllArgsConstructor
public class WishlistShopResponse {
    private Long id;
    private Long  vendorId;
    private String logo;
    private String shopName;
    private Float rating;
}
