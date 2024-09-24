package com.boltomart.customer_service.repository;

import com.boltomart.customer_service.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<Vendor,Long> {
}
