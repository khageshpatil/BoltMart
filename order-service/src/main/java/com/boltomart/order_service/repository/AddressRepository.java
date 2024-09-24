package com.boltomart.order_service.repository;

import com.boltomart.order_service.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByCustomerId(Long customerId);

    List<Address> findByCustomerIdAndStatus(Long customerId, int status);

    Optional<Address> findByCustomerIdAndId(Long customerId, Long addressId);
}
