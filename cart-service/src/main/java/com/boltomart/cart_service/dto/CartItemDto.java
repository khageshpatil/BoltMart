package com.boltomart.cart_service.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CartItemDto {

    private Long productId;
    private Long productVariationId;
    private int quantity;

}