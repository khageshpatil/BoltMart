package com.boltomart.customer_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boltomart.customer_service.exception.CustomerServiceException;
import com.boltomart.customer_service.response.CustomerResponse;
import com.boltomart.customer_service.service.interfaces.CustomerService;


@CrossOrigin("*")
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomerByIdAndStatusActive(@PathVariable Long id) throws CustomerServiceException{
            CustomerResponse customerResponse = customerService.getCustomerByIdAndStatusActive(id);
            return new  ResponseEntity<>(customerResponse,HttpStatus.OK);
    }

}