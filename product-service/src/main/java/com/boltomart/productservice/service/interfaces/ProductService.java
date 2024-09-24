package com.boltomart.productservice.service.interfaces;

import java.math.BigDecimal;
import java.util.List;

import com.boltomart.productservice.entity.Product;
import com.boltomart.productservice.response.ProductResponse;
import com.boltomart.productservice.response.ProductSearchResponse;

public interface ProductService {
	
	public List<ProductResponse> getAllProducts() throws Exception;
	public ProductResponse getProductById(Long id) throws Exception;
    public ProductResponse addProduct(Product product) throws Exception;
    public List<ProductResponse> getVendorProducts(Integer vendorId) throws Exception;
	public List<ProductSearchResponse> searchProducts( Double longitude, Double latitude, Double radius, String productName, String shopName,
			String categoryName, BigDecimal minPrice, BigDecimal maxPrice);
	public List<ProductSearchResponse> getProductsByCategory(Long categoryId, Double latitude, Double longitude,
			Double radius) throws Exception;
	
    
}
