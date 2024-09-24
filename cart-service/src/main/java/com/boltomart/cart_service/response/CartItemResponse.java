package com.boltomart.cart_service.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemResponse {

    private Long productId;
    private Long productVariationId;
    private String productName;
    private String productVariationName;
    private String productVariationValue;
    private String imgUrl;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
}