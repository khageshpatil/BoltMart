package com.boltomart.cart_service.repository;

import com.boltomart.cart_service.entity.Cart;
import com.boltomart.cart_service.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {
    Optional<Cart> findByCustomerIdAndStatus(Long customerId, Integer status);
    Optional<Cart> findByCustomerId(Long customerId);
    void deleteByCustomerId(Long customerId);




}
