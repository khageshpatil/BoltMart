package com.boltomart.vendor_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.boltomart.vendor_service.entity.Vendor;

import java.util.List;


public interface VendorRepository extends JpaRepository<Vendor, Long> {

    // JPQL Query vendors by status of their active sessions
    @Query("SELECT DISTINCT v FROM Vendor v JOIN v.sessions s WHERE s.status = :status")
    List<Vendor> findByActiveSessionStatus(@Param("status") Integer status);

    @Query("SELECT DISTINCT v FROM Vendor v JOIN v.sessions s WHERE v.id = :id AND s.status = :status")
    Vendor findByIdAndActiveSessionStatus(@Param("id") Long id, @Param("status") Integer status);


    @Query("SELECT DISTINCT v FROM Vendor v JOIN v.sessions s WHERE v.address.pincode = :pincode AND s.status = :status")
    List<Vendor> findByAddress_PincodeAndActiveSessionStatus(@Param("pincode") Integer pincode, @Param("status") Integer status);
}
