package com.boltomart.cart_service.repository;

import com.boltomart.cart_service.entity.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemsRepository extends JpaRepository<CartItems,Long> {
    Optional<CartItems> findByProductVariationIdAndCartId(Long productVariationId, Long id);
    List<CartItems> findByCartId(long cartId);

    Optional<CartItems> findByProductIdAndCartId(Long productId, Long id);
}
