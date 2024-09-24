package com.boltomart.order_service.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerOrderResponse {
    private Long customerId;
    private List<OrderResponse> orders;
}