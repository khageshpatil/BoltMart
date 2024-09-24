package com.boltomart.order_service.controller;


import com.boltomart.order_service.dto.OrderPlaceDto;
import com.boltomart.order_service.enums.OrderStatus;
import com.boltomart.order_service.enums.PaymentMethod;
import com.boltomart.order_service.exception.OrderException;
import com.boltomart.order_service.response.ApiResponse;
import com.boltomart.order_service.response.CustomerOrderResponse;
import com.boltomart.order_service.response.OrderResponse;
import com.boltomart.order_service.service.interfaces.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin("*")
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrderFromCart(@RequestBody OrderPlaceDto orderPlaceDto) throws OrderException {
        Long customerId = orderPlaceDto.getCustomerId();
        Long addressId = orderPlaceDto.getAddressId();
        PaymentMethod paymentMethod = orderPlaceDto.getPaymentMethod();
        OrderResponse orderResponse = orderService.createOrderFromCart(customerId, addressId, paymentMethod);
        ApiResponse apiResponse = new ApiResponse("Order created successfully with payment method: " + paymentMethod, true);
        return new ResponseEntity<>(orderResponse, HttpStatus.OK);
    }

    @GetMapping("{order_id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long order_id) throws Exception {
        OrderResponse orderResponse = orderService.getOrderById(order_id);
        return new ResponseEntity<>(orderResponse, HttpStatus.OK);
    }

    @GetMapping("/customer/{customer_id}")
    public ResponseEntity<CustomerOrderResponse> getOrdersByCustomerId(@PathVariable Long customer_id) throws OrderException {
        CustomerOrderResponse orders = orderService.getOrdersByCustomerId(customer_id);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PutMapping("/{order_id}/status")
    public ResponseEntity<ApiResponse> updateOrderStatus(@PathVariable Long order_id, @RequestParam OrderStatus orderStatus) throws OrderException {
        orderService.updateOrderStatus(order_id, orderStatus);
        ApiResponse apiResponse = new ApiResponse("Order status updated successfully", true);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{order_id}")
    public ResponseEntity<ApiResponse> cancelOrder(@PathVariable Long order_id) throws OrderException {
        orderService.cancelOrder(order_id);
        ApiResponse apiResponse = new ApiResponse("Order has been cancelled successfully", true);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() throws OrderException {
        List<OrderResponse> orders = orderService.getAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

}