package com.boltomart.customer_service.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductReviewResponse {
    private Long id;
    private Long productId;
    private Long customerId;
    private int rating;
    private String comment;
    private int status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
