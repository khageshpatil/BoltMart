package com.boltomart.auth_service.service;

import com.boltomart.auth_service.entity.Customer;
import com.boltomart.auth_service.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerService implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByPhoneNumber(phoneNumber);
        if (customer == null) {
            throw new UsernameNotFoundException("Customer not found with phone number: " + phoneNumber);
        }

        // Assuming 'phoneNumber' is the username and you have a password field in your 'User' entity
        return User.withUsername(customer.getPhoneNumber())
                .password("") // Typically, password would be fetched from the customer entity, but here it's left empty
                .roles() // Assigning a role (optional)
                .accountLocked(false)
                .accountExpired(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }

    // To check if customer exists or not
    public boolean isCustomerExists(String phoneNumber) {
        return customerRepository.findByPhoneNumber(phoneNumber) != null;
    }

}
