package com.boltomart.customer_service.service.interfaces;

import com.boltomart.customer_service.dto.ProductReviewDto;
import com.boltomart.customer_service.dto.ShopReviewDto;
import com.boltomart.customer_service.response.ProductReviewResponse;
import com.boltomart.customer_service.response.ShopReviewResponse;

import java.util.List;

public interface ShopReviewService {
    ShopReviewResponse createReview(ShopReviewDto shopReviewDto);

    ShopReviewResponse getReview(Long id);

    List<ShopReviewResponse> getReviewsByCustomerId(Long custId);
    List<ShopReviewResponse> getReviewsByVendorId(Long vendorId);

    boolean deleteReview(Long id);
}
