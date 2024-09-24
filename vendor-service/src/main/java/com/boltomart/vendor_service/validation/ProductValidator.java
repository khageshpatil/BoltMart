package com.boltomart.vendor_service.validation;

import com.boltomart.vendor_service.dto.ProductUpdateRequest;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ProductValidator {

    public void validateProductUpdateRequest(ProductUpdateRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Product update request cannot be null");
        }
        if (request.getUnitPrice() == null || request.getUnitPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Unit price must be non-negative");
        }
        if (request.getStock() < 0) {
            throw new IllegalArgumentException("Stock must be non-negative");
        }
    }

    public void validateVendorId(Long vendorId) {
        if (vendorId == null || vendorId <= 0) {
            throw new IllegalArgumentException("Invalid vendor ID");
        }
    }

    public void validatePrice(BigDecimal minPrice, BigDecimal maxPrice) {
        if (minPrice != null && minPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Minimum price cannot be negative");
        }
        if (maxPrice != null && maxPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Maximum price cannot be negative");
        }
        if (minPrice != null && maxPrice != null && minPrice.compareTo(maxPrice) > 0) {
            throw new IllegalArgumentException("Minimum price cannot be greater than maximum price");
        }
    }
}
