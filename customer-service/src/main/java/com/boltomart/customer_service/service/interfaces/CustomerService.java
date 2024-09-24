package com.boltomart.customer_service.service.interfaces;

import com.boltomart.customer_service.dto.AddressDto;
import com.boltomart.customer_service.entity.Customer;
import com.boltomart.customer_service.exception.CustomerServiceException;
import com.boltomart.customer_service.response.AddressResponse;
import com.boltomart.customer_service.response.CustomerResponse;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    CustomerResponse getCustomerByIdAndStatusActive(Long customerId) throws CustomerServiceException;


}
