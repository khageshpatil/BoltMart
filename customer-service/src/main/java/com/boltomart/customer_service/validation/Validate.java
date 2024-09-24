package com.boltomart.customer_service.validation;

import com.boltomart.customer_service.entity.Customer;
import com.boltomart.customer_service.exception.CustomerServiceException;
import com.boltomart.customer_service.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
public class Validate {
    public void phoneNumber(String phoneNumber) throws CustomerServiceException {
        if (phoneNumber == null || phoneNumber.length() < 10 || phoneNumber.length() > 13) {
            throw new CustomerServiceException("Invalid phone number. Phone number must be between 10 and 13 characters.");
        }
    }
}
