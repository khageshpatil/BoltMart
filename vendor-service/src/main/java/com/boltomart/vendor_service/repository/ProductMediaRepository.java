package com.boltomart.vendor_service.repository;

import com.boltomart.vendor_service.entity.ProductMedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductMediaRepository extends JpaRepository<ProductMedia, Long> {
    
    Optional<ProductMedia> findById(Long id);

    
}
