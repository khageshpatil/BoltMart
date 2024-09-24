package com.boltomart.vendor_service.response;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReviewResponse {
    private Long id;
    private ProductResponse product;
    private CustomerResponse customer;
    private String reviewText;
    private int rating;
    private int status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}