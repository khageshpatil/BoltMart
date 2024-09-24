package com.boltomart.auth_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.boltomart.auth_service.entity.Vendor;


@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long>{

//	Vendor findByEmail(String email);

	Vendor findByPhoneNumber(String phoneNumber);
}
