package com.boltomart.productservice.repository;

import com.boltomart.productservice.entity.ProductReview;
import com.boltomart.productservice.response.ProductReviewResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductReviewRepository extends JpaRepository<ProductReview,Integer> {

    @Query("SELECT new com.boltomart.productservice.response.ProductReviewResponse(r.id,r.product.id,r.customer.id, r.rating, r.reviewText, r.status, r.createdAt, r.updatedAt) FROM ProductReview r WHERE r.customer.id = :customerId and r.status=20")
    List<ProductReviewResponse> findByCustomer_Id(@Param("customerId") Long customerId);

    @Query("SELECT new com.boltomart.productservice.response.ProductReviewResponse(r.id,r.product.id,r.customer.id, r.rating, r.reviewText, r.status, r.createdAt, r.updatedAt) FROM ProductReview r WHERE r.product.id = :productId and r.status=20")
    List<ProductReviewResponse> findByProduct_Id(@Param("productId") Long productId);

    ProductReview findByIdAndStatus(Long id, int active);

    @Query("SELECT AVG(r.rating) FROM ProductReview r WHERE r.product.id = :productId")
    Float findAverageRatingByProductId(@Param("productId") Long productId);
}

