package com.boltomart.cart_service.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartResponse {

    private Long cartId;
    private Long customerId;
    private List<CartItemResponse> cartItems;
    private BigDecimal totalPrice;
}