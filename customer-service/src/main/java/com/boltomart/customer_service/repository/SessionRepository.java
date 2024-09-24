package com.boltomart.customer_service.repository;

import com.boltomart.customer_service.entity.Session;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface SessionRepository extends JpaRepository<Session,Long> {
//    @Query(value = "SELECT * FROM sessions s WHERE s.customer_id = :customerId AND s.is_logged_in = :isLoggedIn", nativeQuery = true)
//    List<Session> findByCustomerIdAndIsLoggedIn(@Param("customerId") Long customerId, @Param("isLoggedIn") boolean isLoggedIn);

    List<Session> findByCustomerIdAndIsLogged(Long id, boolean isLoggedin);
}
