package com.boltomart.productservice.controller;

import com.boltomart.productservice.exception.ProductServiceException;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.boltomart.productservice.entity.Product;

import com.boltomart.productservice.response.ProductResponse;
import com.boltomart.productservice.response.ProductSearchResponse;
import com.boltomart.productservice.service.interfaces.ProductService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class ProductController {

	@Autowired
	private ProductService productService;

	@GetMapping("/products")
	public ResponseEntity<List<ProductResponse>> getAllProducts() throws Exception {
		ResponseEntity<List<ProductResponse>> response;
		try {
			List<ProductResponse> products = productService.getAllProducts();
			response = ResponseEntity.ok(products);
		} catch (Exception e) {
			throw new ProductServiceException("Error retrieving products: " + e.getMessage());
		}
		return response;
	}

	@GetMapping("/products/{productId}")
	public ResponseEntity<ProductResponse> getProductById(@PathVariable Long productId) throws Exception {
		ResponseEntity<ProductResponse> response = null;
		try {
			if (productId != 0 || productId != null) {
				ProductResponse product = productService.getProductById(productId);
				if (product != null) {
					response = ResponseEntity.ok(product);
				} else {
					response = ResponseEntity.notFound().build();
				}
			} else {
				throw new ProductServiceException("Product Id is not found");
			}
		} catch (Exception e) {
			throw new ProductServiceException("Error retrieving product: " + e.getMessage());
		}
		return response;
	}

	@PostMapping("/vendor/products")
	public ResponseEntity<String> addProduct(@RequestBody Product product) throws Exception {
		ResponseEntity<String> response = null;
		try {
			if (product != null) {
				ProductResponse productResponse = productService.addProduct(product);
				if(productResponse != null)
					response = ResponseEntity.status(HttpStatus.CREATED).body("Product added successfully.");
				else
					response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Product could not be created");
			}
		} catch (Exception e) {
			throw new ProductServiceException("Error creating product: " + e.getMessage());
		}
		return response;
	}

	@GetMapping("/vendor/products")
	public ResponseEntity<List<ProductResponse>> getVendorProducts(@RequestParam Integer vendorId) throws Exception {
		ResponseEntity<List<ProductResponse>> response;
		try {
			if (vendorId != null && vendorId != 0) {
				List<ProductResponse> vendorProducts = productService.getVendorProducts(vendorId);
				response = ResponseEntity.ok(vendorProducts);
			} else {
				throw new ProductServiceException("Vendor Id not found");
			}
		} catch (Exception e) {
			throw new ProductServiceException("Error retrieving vendor products: " + e.getMessage());
		}
		return response;
	}
	
	@GetMapping("/products/search")
    public ResponseEntity<Object> searchProducts(
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) String shopName,
            @RequestParam(required = false) String categoryName,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam Double CustomersLongitude,
            @RequestParam Double CustomersLatitude,
            @RequestParam (defaultValue = "3000.0") Double radius) {

        try {
        	if (CustomersLongitude == null || CustomersLatitude == null) {
                return ResponseEntity.badRequest().body("Longitude and latitude must be provided.");
            }

            if (CustomersLongitude < -180 || CustomersLongitude > 180 || CustomersLatitude < -90 || CustomersLatitude > 90) {
                return ResponseEntity.badRequest().body("Longitude and latitude must be within valid ranges.");
            }

        
             if (radius < 0) {
                 return ResponseEntity.badRequest().body("Radius must be a positive number.");
             }
            List<ProductSearchResponse> products = productService.searchProducts(CustomersLongitude, CustomersLatitude, radius, productName, shopName, categoryName, minPrice, maxPrice);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving products: " + e.getMessage());
        }
    }
	
	@GetMapping("products/by-category/{category_id}")
    public ResponseEntity<Object> getProductsByCategory(
            @PathVariable("category_id") Long categoryId,
            @RequestParam Double CustomersLatitude,
            @RequestParam Double CustomersLongitude,
            @RequestParam (defaultValue = "3000.0") Double radius) {
	try {	if (CustomersLongitude == null || CustomersLatitude == null) {
            return ResponseEntity.badRequest().body("Longitude and latitude must be provided.");
        }

        if (CustomersLongitude < -180 || CustomersLongitude > 180 || CustomersLatitude < -90 || CustomersLatitude > 90) {
            return ResponseEntity.badRequest().body("Longitude and latitude must be within valid ranges.");
        }
        List<ProductSearchResponse> products= productService.getProductsByCategory(categoryId, CustomersLatitude, CustomersLongitude, radius);
        return ResponseEntity.ok(products);
	}catch(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving products: " + e.getMessage());
    }
	}
	
}
