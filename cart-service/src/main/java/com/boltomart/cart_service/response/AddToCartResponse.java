package com.boltomart.cart_service.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddToCartResponse {

    private Long cartId;
    private Long customerId;
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
