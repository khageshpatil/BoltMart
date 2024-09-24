package com.boltomart.auth_service.repository;

import com.boltomart.auth_service.entity.Customer;
import com.boltomart.auth_service.entity.Session;
import com.boltomart.auth_service.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SessionRepository extends JpaRepository<Session,Long> {
    Session findByCustomerAndDevice(Customer customer, String device);
    Session findByVendorAndDevice(Vendor vendor,String device);
}
