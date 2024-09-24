package com.boltomart.customer_service.repository;

import com.boltomart.customer_service.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
