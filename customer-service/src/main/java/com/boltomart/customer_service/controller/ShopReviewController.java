package com.boltomart.customer_service.controller;

import com.boltomart.customer_service.dto.ShopReviewDto;
import com.boltomart.customer_service.exception.CustomerServiceException;
import com.boltomart.customer_service.response.ShopReviewResponse;
import com.boltomart.customer_service.service.interfaces.ShopReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/vendor/reviews")
public class ShopReviewController {

    @Autowired
    private ShopReviewService shopReviewService;

    @PostMapping
    public ResponseEntity<ShopReviewResponse> createReview(@Valid @RequestBody ShopReviewDto reviewRequest) {
        ShopReviewResponse shopReviewResponse = shopReviewService.createReview(reviewRequest);
        return new ResponseEntity<>(shopReviewResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShopReviewResponse> getReview(@PathVariable Long id) {
        ShopReviewResponse shopReviewResponse = shopReviewService.getReview(id);
        return new ResponseEntity<>(shopReviewResponse, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable Long id)throws CustomerServiceException {

        boolean result = shopReviewService.deleteReview(id);
        if (result) {
            return new ResponseEntity<>("Review deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Deletion failed: Review not found!!!", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<ShopReviewResponse>> getReviewsByCustomerId(@PathVariable Long customerId) {
        List<ShopReviewResponse> reviews = shopReviewService.getReviewsByCustomerId(customerId);
        return ResponseEntity.ok(reviews);
    }
    @GetMapping("/shops/{shopId}")
    public ResponseEntity<List<ShopReviewResponse>> getReviewsByShopId(@PathVariable Long shopId) {
        List<ShopReviewResponse> reviews = shopReviewService.getReviewsByVendorId(shopId);
        return ResponseEntity.ok(reviews);
    }
}
