package com.boltomart.auth_service.service;

import com.boltomart.auth_service.entity.Vendor;
import com.boltomart.auth_service.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class VendorService implements UserDetailsService {

    @Autowired
    private VendorRepository vendorRepository;

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        Vendor vendor = vendorRepository.findByPhoneNumber(phoneNumber);
        if (vendor == null) {
            throw new UsernameNotFoundException("Vendor not found with phone number: " + phoneNumber);
        }

        // Assuming 'phoneNumber' is the username and you have a password field in your 'Vendor' entity
        return User.withUsername(vendor.getPhoneNumber())
                .password("") // Typically, password would be fetched from the vendor entity, but here it's left empty
                .roles() // Assigning a role (optional)
                .accountLocked(false)
                .accountExpired(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }
}
