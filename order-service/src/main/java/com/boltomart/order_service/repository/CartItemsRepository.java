package com.boltomart.order_service.repository;

import com.boltomart.order_service.entity.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemsRepository extends JpaRepository<CartItems,Long> {
}
