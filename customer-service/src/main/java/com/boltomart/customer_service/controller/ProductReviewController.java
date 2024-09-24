package com.boltomart.customer_service.controller;

import com.boltomart.customer_service.dto.ProductReviewDto;
import com.boltomart.customer_service.exception.CustomerServiceException;
import com.boltomart.customer_service.response.ProductReviewResponse;
import com.boltomart.customer_service.service.interfaces.ProductReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/product/reviews")
public class ProductReviewController {

    @Autowired
    private ProductReviewService productReviewService;

    @PostMapping
    public ResponseEntity<ProductReviewResponse> createReview(@Valid @RequestBody ProductReviewDto reviewRequest) {
        ProductReviewResponse productReviewResponse = productReviewService.createReview(reviewRequest);
        return new ResponseEntity<>(productReviewResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductReviewResponse> getReview(@PathVariable Long id) {
        ProductReviewResponse productReviewResponse = productReviewService.getReview(id);
        return new ResponseEntity<>(productReviewResponse, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable Long id)throws CustomerServiceException {

        boolean result = productReviewService.deleteReview(id);
        if (result) {
            return new ResponseEntity<>("Review deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Deletion failed: Review not found!!!", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<ProductReviewResponse>> getReviewsByCustomerId(@PathVariable Long customerId) {
        List<ProductReviewResponse> reviews = productReviewService.getReviewsByCustomerId(customerId);
        return ResponseEntity.ok(reviews);
    }
    @GetMapping("/products/{productId}")
    public ResponseEntity<List<ProductReviewResponse>> getReviewsByProductId(@PathVariable Long productId) {
        List<ProductReviewResponse> reviews = productReviewService.getReviewsByProductId(productId);
        return ResponseEntity.ok(reviews);
    }
}
