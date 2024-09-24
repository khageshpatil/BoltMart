package com.boltomart.order_service.repository;


import com.boltomart.order_service.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {
    Optional<Cart> findByCustomerIdAndStatus(Long customerId, Integer status);
    Cart findByCustomerId(Long customerId);


}
