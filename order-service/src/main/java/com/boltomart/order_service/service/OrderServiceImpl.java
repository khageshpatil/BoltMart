package com.boltomart.order_service.service;

import com.boltomart.order_service.entity.*;
import com.boltomart.order_service.enums.OrderStatus;
import com.boltomart.order_service.enums.PaymentMethod;
import com.boltomart.order_service.exception.OrderException;
import com.boltomart.order_service.helper.Helper;
import com.boltomart.order_service.repository.*;
import com.boltomart.order_service.response.CustomerOrderResponse;
import com.boltomart.order_service.response.OrderResponse;
import com.boltomart.order_service.service.interfaces.OrderService;
import com.boltomart.order_service.validate.Validate;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private OrderItemsRepository orderItemsRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private CartItemsRepository cartItemsRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private Validate validate;

    @Autowired
    private Helper helper;

    @Transactional
    @Override
    public OrderResponse createOrderFromCart(Long customerId, Long addressId, PaymentMethod paymentMethod) throws OrderException {
        Customer customer = validate.getCustomerById(customerId);

        Cart cart = customer.getCart();
        if (cart == null || cart.getCartItems().isEmpty()) {
            throw new OrderException("Can't place order with empty cart");
        }

        Address address = addressRepository.findByCustomerIdAndId(customerId, addressId)
                .orElseThrow(() -> new OrderException("Address not found"));

        Order order = helper.createOrder(customer, address, cart);

        // Use ModelMapper to map CartItems to OrderItems
        List<OrderItems> orderItems = cart.getCartItems().stream()
                .map(cartItem -> modelMapper.map(cartItem, OrderItems.class))
                .peek(orderItem -> {
                    orderItem.setOrder(order);
                })
                .toList();

        BigDecimal totalAmount = orderItems.stream()
                .map(orderItem -> orderItem.getUnitPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotalAmount(totalAmount);
        // Process payment
        Payment payment = helper.processPayment(order, paymentMethod);
        // Save the payment entity
        paymentRepository.save(payment);
        order.setPayment(payment);
        order.setOrderItems(orderItems);
        orderRepository.save(order);
        orderItemsRepository.saveAll(orderItems);

        // Clear the cart after order creation
        cartItemsRepository.deleteAll(cart.getCartItems());
        cartRepository.delete(cart);

        logger.info("Order created successfully for customerId: {}, with payment method: {}", customerId, paymentMethod);
        return helper.orderResponse(order);
    }

    @Override
    public OrderResponse getOrderById(Long orderId) throws OrderException {
        validate.order(orderId);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderException("Order not found"));
        return helper.orderResponse(order);
    }

    @Override
    public CustomerOrderResponse getOrdersByCustomerId(Long customerId) throws OrderException {
        validate.getCustomerById(customerId);

        List<Order> orders = orderRepository.findByCustomerId(customerId);

        if (orders.isEmpty()) {
            throw new OrderException("No orders found for customer ID: " + customerId);
        }

        List<OrderResponse> orderResponses = orders.stream()
                .map(helper::orderResponse)
                .collect(Collectors.toList());

        return new CustomerOrderResponse(customerId, orderResponses);
    }

    @Override
    public void updateOrderStatus(Long orderId, OrderStatus orderStatus) throws OrderException {
        validate.order(orderId);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderException("Order not found with the id " + orderId));

        order.setOrderStatus(orderStatus);
        orderRepository.save(order);
    }
    @Transactional
    @Override
    public void cancelOrder(Long orderId) throws OrderException {
        validate.order(orderId);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderException("Order not found with id: " + orderId));
//        order.setOrderStatus(OrderStatus.CANCELLED);
        orderItemsRepository.deleteAll(order.getOrderItems());
        paymentRepository.delete(order.getPayment());
        orderRepository.delete(order);
    }

    @Override
    public List<OrderResponse> getAllOrders() throws OrderException {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(helper::orderResponse)
                .collect(Collectors.toList());
    }
}
