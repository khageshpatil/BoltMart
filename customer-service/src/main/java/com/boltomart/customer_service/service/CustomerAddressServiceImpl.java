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
import com.boltomart.customer_service.service.interfaces.CustomerAddressService;
import com.boltomart.customer_service.validation.Validate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class CustomerAddressServiceImpl implements CustomerAddressService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private Validate validate;
    @Autowired
    private Helper helper;

    @Override
    public List<AddressResponse> getActiveCustomerAddresses(Long customerId) throws CustomerServiceException {
        // validate customer
        Customer customer = helper.findCustomerById(customerId);

        // Map and return active addresses
        return helper.mapAddresses(customer.getId());
    }

    @Override
    public AddressResponse addAddressForCustomer(Long customerId, AddressDto addressDto) throws CustomerServiceException {
        // Find the customer by ID or throw exception if not found
        Customer customer = helper.findCustomerById(customerId);
        validate.phoneNumber(addressDto.getPhoneNumber());
        // Check if customer has active session
        List<Session> sessions = sessionRepository.findByCustomerIdAndIsLogged(customer.getId(), true);

        if (sessions.isEmpty()) {
            throw new CustomerServiceException("Customer is not active with ID: " + customerId);
        }
        // Map and save address
        Address address = modelMapper.map(addressDto, Address.class);
        address.setCustomer(customer);
        address.setStatus(ApplicationConstant.ACTIVE);
        Address savedAddress = addressRepository.save(address);

        // Map saved address to response DTO
        return modelMapper.map(savedAddress, AddressResponse.class);
    }

    @Override
    public AddressResponse updateAddressForCustomer(Long addressId, AddressDto addressDto) throws CustomerServiceException {
        validate.phoneNumber(addressDto.getPhoneNumber());

        Address addressExist= addressRepository.findByIdAndStatus(addressId,ApplicationConstant.ACTIVE);
        if(addressExist==null)
            throw new CustomerServiceException("Address is not active with ID: " + addressId);
        Address address = modelMapper.map(addressDto, Address.class);
        address.setId(addressId);
        address.setStatus(ApplicationConstant.ACTIVE);
        address.setUpdatedAt(LocalDateTime.now());
        Address savedAddress = addressRepository.save(address);
        return modelMapper.map(savedAddress, AddressResponse.class);
    }

    @Override
    public AddressResponse getActiveCustomerAddress(Long addressId) throws CustomerServiceException {
        Address address =addressRepository.findByIdAndStatus(addressId,ApplicationConstant.ACTIVE);
        if(address==null)
            throw new CustomerServiceException("Address is not active with ID: " + addressId);

        // Map and return active addresses
        return modelMapper.map(address, AddressResponse.class);
    }

    @Override
    public boolean deleteAddress(Long id) throws CustomerServiceException {
        Address address =addressRepository.findByIdAndStatus(id,ApplicationConstant.ACTIVE);
        //if address not found
        if(address==null)
           return false;

        address.setStatus(ApplicationConstant.DELETE);
        addressRepository.save(address);
        return true;
    }


}
