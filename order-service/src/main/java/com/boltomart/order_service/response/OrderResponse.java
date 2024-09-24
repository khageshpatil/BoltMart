package com.boltomart.order_service.response;

import com.boltomart.order_service.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    private Long orderId;
    private Long customerId;
    private Long addressId;
    private BigDecimal totalAmount;
    private OrderStatus orderStatus;
    private List<OrderItemResponse> orderItems;
    private PaymentResponse payment;

}