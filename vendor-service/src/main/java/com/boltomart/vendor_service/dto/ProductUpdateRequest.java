package com.boltomart.vendor_service.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdateRequest {
    private String SKU;
    private String productName;
    private String productDescription;
    private Long categoryId;
    private boolean hasVariations;
    private BigDecimal unitPrice;
    private Integer stock;
    private String size;
    private String notes;
    private Integer status;
    private List<ProductVariationRequest> variations;
    private List<ProductMediaRequest> images;
    private LocalDateTime updatedAt;
}
