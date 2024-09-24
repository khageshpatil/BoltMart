package com.boltomart.vendor_service.controller;

import com.boltomart.vendor_service.dto.ProductUpdateRequest;
import com.boltomart.vendor_service.response.ProductResponse;
import com.boltomart.vendor_service.exception.VendorProductException;
import com.boltomart.vendor_service.service.interfaces.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/vendor/{vendorId}/products")
public class VendorProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/search")
    public ResponseEntity<Object> searchProducts(
            @PathVariable Long vendorId,
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) String categoryName,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice) {
        try {
            List<ProductResponse> products = productService.searchProducts(vendorId, productName, categoryName, minPrice, maxPrice);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving products: " + e.getMessage());
        }
    }
    
    @PutMapping("/{productId}")
    public ResponseEntity<Object> updateProduct(
            @PathVariable Long vendorId,
            @PathVariable Long productId,
            @RequestBody ProductUpdateRequest productUpdateRequest) {
        try {
            ProductResponse updatedProduct = productService.updateProduct(vendorId, productId, productUpdateRequest);
            return ResponseEntity.ok("Product Updated Successfully !!");
        } catch (VendorProductException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace(); 
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating product: " + e.getMessage());
        }
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Object> deleteProduct(@PathVariable Long vendorId, @PathVariable Long productId) {
        try {
            productService.deleteProduct(vendorId, productId);
            return ResponseEntity.ok("Product deleted successfully.");
        } catch (VendorProductException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting product: " + e.getMessage());
        }
    }
}
