package com.boltomart.productservice.validations;

import java.time.LocalDateTime;

import com.boltomart.productservice.dto.CategoryDTO;
import com.boltomart.productservice.entity.Product;
import com.boltomart.productservice.exception.ProductServiceException;

public final class Validations {

	public static final void validateProduct(Product product) throws ProductServiceException {
		if (product == null) {
			throw new ProductServiceException("Product cannot be null.");
		}
		if (product.getVendor() == null || product.getVendor().getId() == 0) {
			throw new ProductServiceException("Vendor is mandatory.");
		}
		if (product.getSKU() == null || product.getSKU().trim().isEmpty()) {
			throw new ProductServiceException("SKU is mandatory.");
		}
		if (product.getProductName() == null || product.getProductName().trim().isEmpty()) {
			throw new ProductServiceException("Product name is mandatory.");
		}
		if (product.getUnitPrice() == null) {
			throw new ProductServiceException("Unit price is mandatory.");
		}
		if (product.getCreatedAt() == null) {
			product.setCreatedAt(LocalDateTime.now());
		}
		product.setUpdatedAt(LocalDateTime.now());
	}
	
	public static final void validateCategory(CategoryDTO category) throws ProductServiceException {
		if (category == null) {
			throw new ProductServiceException("Category cannot be null.");
		}
		if (category.getCategoryName() == null || category.getCategoryName().trim().isEmpty()) {
			throw new ProductServiceException("CategoryName is mandatory.");
		}
		if (category.getCreatedAt() == null) {
			category.setCreatedAt(LocalDateTime.now());
		}
		category.setUpdatedAt(LocalDateTime.now());
	}

}
