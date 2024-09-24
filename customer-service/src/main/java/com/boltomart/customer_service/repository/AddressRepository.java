package com.boltomart.customer_service.repository;

import com.boltomart.customer_service.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByCustomerId(Long customerId);

    Address findByIdAndStatus(Long addressId,int status);
    List<Address> findByCustomerIdAndStatus(Long customerId, int status);
}
