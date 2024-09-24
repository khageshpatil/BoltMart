package com.boltomart.cart_service.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CartDto {

    private Long customerId;
    private CartItemDto cartItems;

}