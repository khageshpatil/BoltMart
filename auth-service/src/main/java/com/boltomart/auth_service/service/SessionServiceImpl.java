package com.boltomart.auth_service.service;

import com.boltomart.auth_service.constants.ApplicationConstant;
import com.boltomart.auth_service.dto.AuthDto;
import com.boltomart.auth_service.entity.Customer;
import com.boltomart.auth_service.entity.Session;
import com.boltomart.auth_service.entity.Vendor;
import com.boltomart.auth_service.repository.CustomerRepository;
import com.boltomart.auth_service.repository.SessionRepository;
import com.boltomart.auth_service.repository.VendorRepository;
import com.boltomart.auth_service.service.interfaces.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service


public class SessionServiceImpl implements SessionService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private VendorRepository vendorRepository;
    @Autowired
    private SessionRepository sessionRepository;
    @Override
    public Session createCustomerSession(AuthDto authDto) {
        Customer customer = customerRepository.findByPhoneNumber(authDto.getPhoneNumber());

        Session session= sessionRepository.findByCustomerAndDevice(customer, authDto.getDevice());


        if (session!=null) {

            // Update the session with new login time and IP address
            session.setLoginTime(LocalDateTime.now());

            session.setIpAddress(authDto.getIpAddress());

            session.setStatus(ApplicationConstant.ACTIVE);
            session.setLogged(true);
        } else {
            // Create a new session if no existing session is found
            session = new Session();
            session.setCustomer(customer);
            session.setLoginTime(LocalDateTime.now());
            session.setStatus(ApplicationConstant.ACTIVE);
            session.setLogged(true);

            session.setDevice(authDto.getDevice());
            session.setIpAddress(authDto.getIpAddress());

        }

        return session;
    }

    @Override
    public Session createVendorSession(AuthDto authDto) {
        Vendor vendor = vendorRepository.findByPhoneNumber(authDto.getPhoneNumber());

        Session session= sessionRepository.findByVendorAndDevice(vendor, authDto.getDevice());

        if (session!=null) {

            // Update the session with new login time and IP address
            session.setLoginTime(LocalDateTime.now());

            session.setIpAddress(authDto.getIpAddress());
            session.setStatus(ApplicationConstant.ACTIVE);
            session.setLogged(true);
        } else {
            // Create a new session if no existing session is found
            session = new Session();
            session.setVendor(vendor);
            session.setLoginTime(LocalDateTime.now());
            session.setStatus(ApplicationConstant.ACTIVE);
            session.setLogged(true);
            session.setDevice(authDto.getDevice());
            session.setIpAddress(authDto.getIpAddress());
            session.setDevice(authDto.getDevice());
            session.setIpAddress(authDto.getIpAddress());

        }

        return session;
    }
}
