package com.boltomart.customer_service.service;

import com.boltomart.customer_service.constants.ApplicationConstant;
import com.boltomart.customer_service.dto.AddressDto;
import com.boltomart.customer_service.entity.Address;
import com.boltomart.customer_service.entity.Customer;
import com.boltomart.customer_service.entity.Session;
import com.boltomart.customer_service.exception.CustomerServiceException;
import com.boltomart.customer_service.helper.Helper;
import com.boltomart.customer_service.repository.AddressRepository;
import com.boltomart.customer_service.repository.CustomerRepository;
import com.boltomart.customer_service.repository.SessionRepository;
import com.boltomart.customer_service.response.AddressResponse;
import com.boltomart.customer_service.response.CustomerResponse;
import com.boltomart.customer_service.service.interfaces.CustomerAddressService;
import com.boltomart.customer_service.service.interfaces.CustomerService;
import com.boltomart.customer_service.validation.Validate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private Helper helper;
    @Autowired
    private CustomerAddressService customerAddressService;
    @Override
    public CustomerResponse getCustomerByIdAndStatusActive(Long customerId) throws CustomerServiceException {
        // validate customer
        Customer customer = helper.findCustomerById(customerId);

        // Find active sessions for the customer
        List<Session> sessions = sessionRepository.findByCustomerIdAndIsLogged(customer.getId(), true);

        // Check if any session is active
        if (sessions.isEmpty()) {
            throw new CustomerServiceException("Customer is not active with ID: " + customerId);
        }

        // Map customer to CustomerResponse
        CustomerResponse response = modelMapper.map(customer, CustomerResponse.class);

        // Map and set active addresses to the response
        List<AddressResponse> activeAddresses = customerAddressService.getActiveCustomerAddresses(customer.getId());
        response.setAddresses(activeAddresses);

        return response;
    }


}
