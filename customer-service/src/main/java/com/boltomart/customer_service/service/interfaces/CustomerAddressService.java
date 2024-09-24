package com.boltomart.customer_service.service.interfaces;

import com.boltomart.customer_service.dto.AddressDto;
import com.boltomart.customer_service.exception.CustomerServiceException;
import com.boltomart.customer_service.response.AddressResponse;

import java.util.List;

public interface CustomerAddressService {
    List<AddressResponse> getActiveCustomerAddresses(Long customerId) throws CustomerServiceException;
    AddressResponse addAddressForCustomer(Long customerId, AddressDto addressDto) throws CustomerServiceException;

    AddressResponse updateAddressForCustomer(Long customerId, AddressDto addressDto) throws CustomerServiceException;;

    AddressResponse getActiveCustomerAddress(Long addressId) throws CustomerServiceException;

    boolean deleteAddress(Long id) throws CustomerServiceException;
}
