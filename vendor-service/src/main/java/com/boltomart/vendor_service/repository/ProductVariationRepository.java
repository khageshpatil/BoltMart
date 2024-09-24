package com.boltomart.vendor_service.repository;

import com.boltomart.vendor_service.entity.ProductVariation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductVariationRepository extends JpaRepository<ProductVariation, Long> {
    
    Optional<ProductVariation> findById(Long id);

}
