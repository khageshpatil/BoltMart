package com.boltomart.vendor_service.service.interfaces;
import java.math.BigDecimal;
import java.util.List;

import com.boltomart.vendor_service.dto.ProductUpdateRequest;
import com.boltomart.vendor_service.response.ProductResponse;

public interface ProductService {

	 List<ProductResponse> searchProducts(Long vendorId, String productName, String categoryName, BigDecimal minPrice, BigDecimal maxPrice);
	 ProductResponse updateProduct(Long productId, Long productId2, ProductUpdateRequest productRequest) throws Exception;
	void deleteProduct(Long vendorId, Long productId) throws Exception;
	
	 
}
