package com.boltomart.cart_service.repository;

import com.boltomart.cart_service.entity.ProductVariation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductVariationRepository extends JpaRepository<ProductVariation,Long> {
    ProductVariation findByProductId(long productId);
}
