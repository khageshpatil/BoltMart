package com.boltomart.order_service.validate;

import com.boltomart.order_service.entity.Customer;
import com.boltomart.order_service.exception.OrderException;
import com.boltomart.order_service.repository.CustomerRepository;
import com.boltomart.order_service.repository.ProductVariationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Validate {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductVariationRepository productVariationRepository;

    public Customer getCustomerById(Long customerId) throws OrderException {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new OrderException("Customer not found with id: " + customerId));
    }

    public void order(Long orderId) throws OrderException {
        if(orderId<0){
            throw new OrderException("OrderId should be positive");
        }
    }
}
