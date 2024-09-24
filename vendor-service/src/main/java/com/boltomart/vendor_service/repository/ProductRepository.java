package com.boltomart.vendor_service.repository;

import com.boltomart.vendor_service.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    //JPQL Query Used
    @Query("SELECT p FROM Product p WHERE p.vendor.id = :vendorId " +
           "AND (:productName IS NULL OR LOWER(p.productName) LIKE LOWER(CONCAT('%', :productName, '%'))) " +
           "AND (:categoryName IS NULL OR LOWER(p.category.categoryName) LIKE LOWER(CONCAT('%', :categoryName, '%'))) " +
           "AND (:minPrice IS NULL OR p.unitPrice >= :minPrice) " +
           "AND (:maxPrice IS NULL OR p.unitPrice <= :maxPrice)")
    List<Product> searchProducts(@Param("vendorId") Long vendorId,
                                 @Param("productName") String productName,
                                 @Param("categoryName") String categoryName,
                                 @Param("minPrice") BigDecimal minPrice,
                                 @Param("maxPrice") BigDecimal maxPrice);
    
    Optional<Product> findByIdAndVendorId(Long productId, Long vendorId);
    List<Product> findByVendorId(Long vendorId);
  
}
