package com.boltomart.customer_service.repository;

import com.boltomart.customer_service.entity.ProductReview;
import com.boltomart.customer_service.entity.ShopReview;
import com.boltomart.customer_service.response.ProductReviewResponse;
import com.boltomart.customer_service.response.ShopReviewResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShopReviewRepository extends JpaRepository<ShopReview,Long> {
    @Query("SELECT new com.boltomart.customer_service.response.ShopReviewResponse(r.id,r.vendor.id,r.customer.id, r.rating, r.reviewText, r.status, r.createdAt, r.updatedAt) FROM ShopReview r WHERE r.customer.id = :customerId and r.status=20")
    List<ShopReviewResponse> findByCustomer_Id(@Param("customerId") Long customerId);

    @Query("SELECT new com.boltomart.customer_service.response.ShopReviewResponse(r.id,r.vendor.id,r.customer.id, r.rating, r.reviewText, r.status, r.createdAt, r.updatedAt) FROM ShopReview r WHERE r.vendor.id = :vendorId and r.status=20")
    List<ShopReviewResponse> findByProduct_Id(@Param("vendorId") Long vendorId);

    ShopReview findByIdAndStatus(Long id, int active);

    @Query("SELECT AVG(r.rating) FROM ShopReview r WHERE r.vendor.id = :vendorId")
    Float findAverageRatingByVendorId(@Param("vendorId") Long vendorId);
}
