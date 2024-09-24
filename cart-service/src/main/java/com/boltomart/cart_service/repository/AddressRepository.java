package com.boltomart.cart_service.repository;

import com.boltomart.cart_service.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByCustomerId(Long customerId);

    List<Address> findByCustomerIdAndStatus(Long customerId, int status);
}
