package com.boltomart.productservice.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReviewResponse {
    private Long id;
    private Long productId;      
    private Long customerId;    
    private String reviewText;
    private int rating;
    private int status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
