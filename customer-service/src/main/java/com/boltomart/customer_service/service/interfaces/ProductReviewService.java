package com.boltomart.customer_service.service.interfaces;

import com.boltomart.customer_service.dto.ProductReviewDto;
import com.boltomart.customer_service.response.ProductReviewResponse;

import java.util.List;

public interface ProductReviewService {
  ProductReviewResponse createReview(ProductReviewDto productReviewDto);

  ProductReviewResponse getReview(Long id);

  List<ProductReviewResponse> getReviewsByCustomerId(Long custId);
  List<ProductReviewResponse> getReviewsByProductId(Long productId);

    boolean deleteReview(Long id);
}
