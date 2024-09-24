package com.boltomart.customer_service.controller;


import com.boltomart.customer_service.dto.AddressDto;
import com.boltomart.customer_service.exception.CustomerServiceException;
import com.boltomart.customer_service.response.AddressResponse;
import com.boltomart.customer_service.service.interfaces.CustomerAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api")
public class CustomerAddressController {
    @Autowired
    private CustomerAddressService customerAddressService;

    @GetMapping("/customer/{id}/addresses")
    public ResponseEntity<List<AddressResponse>> getActiveCustomerAddresses(@PathVariable Long id)throws CustomerServiceException {
        List<AddressResponse> addressResponses = customerAddressService.getActiveCustomerAddresses(id);
        return new  ResponseEntity<>(addressResponses, HttpStatus.OK);
    }

    @PostMapping("customer/{id}/address")
    public ResponseEntity<AddressResponse> addAddressForCustomer(@PathVariable Long id, @RequestBody AddressDto addressDto) throws CustomerServiceException{
        AddressResponse savedAddressResponse = customerAddressService.addAddressForCustomer(id, addressDto);
        return new  ResponseEntity<>(savedAddressResponse,HttpStatus.CREATED);
    }

    @PutMapping("customer/address/{id}")
    public ResponseEntity<AddressResponse> updateAddress(@PathVariable Long id, @RequestBody AddressDto addressDto) throws CustomerServiceException {
        AddressResponse updatedAddress = customerAddressService.updateAddressForCustomer(id, addressDto);
        return ResponseEntity.ok(updatedAddress);
    }

    @GetMapping("customer/address/{addressId}")
    public ResponseEntity<AddressResponse> getActiveCustomerAddress(@PathVariable Long addressId)throws CustomerServiceException {
        AddressResponse addressResponses = customerAddressService.getActiveCustomerAddress(addressId);
        return new  ResponseEntity<>(addressResponses, HttpStatus.OK);
    }

    @DeleteMapping("customer/address/{id}")
    public ResponseEntity<String> deleteCustomerAddress(@PathVariable Long id)throws CustomerServiceException {

        boolean result = customerAddressService.deleteAddress(id);
        if (result) {
            return new ResponseEntity<>("Address deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Deletion failed: Address not found!!!", HttpStatus.BAD_REQUEST);
        }
        }
    }


