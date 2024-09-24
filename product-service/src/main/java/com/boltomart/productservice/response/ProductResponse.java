package com.boltomart.productservice.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ProductResponse {
    private Long id;
    private String SKU;
    private Long vendorId;
    private String productName;
    private String productDescription;
    private ProductCategoryResponse category;
    private boolean hasVariations;
    private BigDecimal unitPrice;
    private Integer stock;
    private String size;
    private String notes;
    private Integer status;
    private List<ProductVariationResponse> variations;
    private List<ProductMediaResponse> images;
    private List<ReviewResponse> reviews;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
