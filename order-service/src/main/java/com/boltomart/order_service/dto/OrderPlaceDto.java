package com.boltomart.order_service.dto;

import com.boltomart.order_service.enums.PaymentMethod;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class OrderPlaceDto {
    private Long customerId;
    private Long addressId;
    private PaymentMethod paymentMethod;
}
