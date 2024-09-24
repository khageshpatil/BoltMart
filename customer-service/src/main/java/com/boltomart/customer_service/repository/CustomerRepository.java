package com.boltomart.customer_service.repository;

import com.boltomart.customer_service.entity.Customer;
import com.boltomart.customer_service.exception.CustomerServiceException;
import com.boltomart.customer_service.response.CustomerResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    
}
