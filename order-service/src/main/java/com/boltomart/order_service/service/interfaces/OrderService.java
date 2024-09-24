package com.boltomart.order_service.service.interfaces;


import com.boltomart.order_service.enums.OrderStatus;
import com.boltomart.order_service.enums.PaymentMethod;
import com.boltomart.order_service.exception.OrderException;
import com.boltomart.order_service.response.CustomerOrderResponse;
import com.boltomart.order_service.response.OrderResponse;

import java.util.List;

public interface OrderService {


    OrderResponse createOrderFromCart(Long customerId, Long addressId, PaymentMethod paymentMethod) throws OrderException;

    OrderResponse getOrderById(Long orderId) throws OrderException;

    CustomerOrderResponse getOrdersByCustomerId(Long customerId) throws OrderException;

    void updateOrderStatus(Long orderId, OrderStatus orderStatus) throws OrderException;

    void cancelOrder(Long orderId) throws OrderException;

    List<OrderResponse> getAllOrders() throws OrderException;
}
