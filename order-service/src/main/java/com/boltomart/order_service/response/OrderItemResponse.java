package com.boltomart.order_service.response;

import com.boltomart.order_service.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponse {

    private Long orderItemId;
    private Long orderId;
    private Long productId;
    private Long productVariationId;
    private String productVariationName;
    private String productVariationValue;
    private String productName;
    private String imgUrl;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;

}