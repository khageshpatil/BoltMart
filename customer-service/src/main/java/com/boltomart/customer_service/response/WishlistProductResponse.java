package com.boltomart.customer_service.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data@AllArgsConstructor
public class WishlistProductResponse {
        private Long id;
        private Long  productId;
        private String productImg;
        private String productName;
        private BigDecimal unitPrice;
        private Float rating;

}


