package com.boltomart.customer_service.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductReviewDto {
    @NotNull(message = "Product ID cannot be null")
    private Long productId;

    @NotNull(message = "User ID cannot be null")
    private Long customerId;

    @Min(value = 1, message = "Rating must be between 1 and 5")
    @Max(value = 5, message = "Rating must be between 1 and 5")
    private int rating;

    @NotBlank(message = "Comment cannot be blank")
    private String reviewText;
}
