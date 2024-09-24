package com.boltomart.order_service.enums;

public enum OrderStatus {

    PENDING_PAYMENT,
    CONFIRMED,
    PROCESSING,
    SHIPPED,
    OUT_FOR_DELIVERY,
    CANCELLED,
    RETURNED,
    REFUNDED,
    FAILED,
    DELIVERED;
}
