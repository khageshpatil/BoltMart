package com.boltomart.auth_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.boltomart.auth_service.entity.Customer;




@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findByPhoneNumber(String phoneNumber);


}
