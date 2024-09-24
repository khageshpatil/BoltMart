package com.boltomart.order_service.helper;


import com.boltomart.order_service.constants.ApplicationConstant;
import com.boltomart.order_service.entity.*;
import com.boltomart.order_service.enums.OrderStatus;
import com.boltomart.order_service.enums.PaymentMethod;
import com.boltomart.order_service.enums.PaymentStatus;
import com.boltomart.order_service.repository.CartItemsRepository;
import com.boltomart.order_service.repository.CartRepository;
import com.boltomart.order_service.repository.PaymentRepository;
import com.boltomart.order_service.response.OrderItemResponse;
import com.boltomart.order_service.response.OrderResponse;
import com.boltomart.order_service.response.PaymentResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class Helper {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemsRepository cartItemsRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Order createOrder(Customer customer, Address address, Cart cart) {
        Order order = new Order();
        order.setCustomer(customer);
        order.setAddress(address);
        order.setTotalAmount(cart.getCartItems().stream()
                .map(CartItems::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        order.setOrderStatus(OrderStatus.PENDING_PAYMENT);
        return order;
    }

    public OrderResponse orderResponse(Order order) {
        OrderResponse orderResponse = modelMapper.map(order, OrderResponse.class);

        // Handle null check for orderItems
        List<OrderItemResponse> orderItems = Optional.ofNullable(order.getOrderItems())
                .orElse(Collections.emptyList())
                .stream()
                .map(item -> {
                    OrderItemResponse itemResponse = modelMapper.map(item, OrderItemResponse.class);
                    itemResponse.setOrderItemId(item.getId()); // Set the orderItemId explicitly
                    itemResponse.setProductId(item.getProduct().getId());
                    itemResponse.setProductName(item.getProduct().getProductName());
                    if(item.getProductVariation()!=null) {
                    itemResponse.setProductVariationId(item.getProductVariation().getId());
                    itemResponse.setProductVariationName(item.getProductVariation().getVariationName());
                    }
                    if (item.getProductVariation()!=null &&!item.getProductVariation().getProduct().getImages().isEmpty()) {
                        itemResponse.setImgUrl(item.getProductVariation().getProduct().getImages().get(0).getImgUrl());
                    } else if (!item.getProduct().getImages().isEmpty()) {
                        itemResponse.setImgUrl(item.getProduct().getImages().get(0).getImgUrl());
                    }
                    return itemResponse;
                })
                .collect(Collectors.toList());

        orderResponse.setOrderItems(orderItems);
        Payment payment = order.getPayment();
        if (payment != null) {
            PaymentResponse paymentResponse = modelMapper.map(payment, PaymentResponse.class);
            orderResponse.setPayment(paymentResponse); // Set the mapped payment in the response
        }

        return orderResponse;
    }

    public Payment processPayment(Order order, PaymentMethod paymentMethod) {
        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setPaymentMethod(paymentMethod);
        payment.setAmount(order.getTotalAmount());
        payment.setStatus(ApplicationConstant.ACTIVE);

        if (paymentMethod == PaymentMethod.CASH_ON_DELIVERY) {
            payment.setPaymentStatus(PaymentStatus.PENDING); // Initial status for COD
        } else {
            payment.setPaymentStatus(PaymentStatus.SUCCESS);
        }

        payment.setTransactionId(UUID.randomUUID().toString()); // Generate a unique transaction ID
        payment.setPaymentDate(LocalDateTime.now());
        // Use ModelMapper to map the Payment entity to PaymentResponse
        return payment;
    }

}

@Component
class CustomBindingInitializer {
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(PaymentMethod.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(PaymentMethod.valueOf(text.toUpperCase()));
            }
        });

        binder.registerCustomEditor(OrderStatus.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(OrderStatus.valueOf(text.toUpperCase()));
            }
        });
    }
}
