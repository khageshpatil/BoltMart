package com.boltomart.customer_service.helper;

import com.boltomart.customer_service.constants.ApplicationConstant;
import com.boltomart.customer_service.entity.Customer;
import com.boltomart.customer_service.exception.CustomerServiceException;
import com.boltomart.customer_service.repository.AddressRepository;
import com.boltomart.customer_service.repository.CustomerRepository;
import com.boltomart.customer_service.response.AddressResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class Helper {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AddressRepository addressRepository;

    public List<AddressResponse> mapAddresses(long customerId) throws CustomerServiceException {
        try {
            return addressRepository.findByCustomerIdAndStatus(customerId, ApplicationConstant.ACTIVE).stream()
                    .map(address -> modelMapper.map(address, AddressResponse.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new CustomerServiceException("Error mapping addresses", e);
        }
    }

    public Customer findCustomerById(Long customerId) throws CustomerServiceException {
        // Check if customerId is null
        if (customerId == null) {
            throw new CustomerServiceException("Customer ID cannot be null");
        }

        // Retrieve customer by ID
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if (optionalCustomer.isEmpty()) {
            throw new CustomerServiceException("Customer not found with ID: " + customerId);
        }

        return optionalCustomer.get();
    }
}
