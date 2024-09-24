package com.boltomart.customer_service.controller;

import com.boltomart.customer_service.dto.ProductWishlistDto;
import com.boltomart.customer_service.dto.ShopWishlistDto;
import com.boltomart.customer_service.response.WishlistProductResponse;
import com.boltomart.customer_service.response.WishlistShopResponse;
import com.boltomart.customer_service.service.interfaces.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers/{customerId}/wishlist/products")
public class ProductWishlistController {

    @Autowired
    private WishlistService wishlistService;

    @PostMapping()
    public ResponseEntity<String> addProductToWishlist(@PathVariable Long customerId, @RequestBody ProductWishlistDto productWishlistDto) {
        productWishlistDto.setCustomerId(customerId);
        String message = wishlistService.addProductToWishlist(productWishlistDto);
        return ResponseEntity.ok(message);
    }

    @GetMapping()
    public ResponseEntity<List<WishlistProductResponse>> getWishlistOfProducts(@PathVariable Long customerId) {
        List<WishlistProductResponse> wishlists = wishlistService.getProductWishlistByCustomerId(customerId);
        return ResponseEntity.ok(wishlists);
    }


    @DeleteMapping("/{productId}")
    public ResponseEntity<String> removeProductFromWishlist(@PathVariable Long customerId, @PathVariable Long productId) {
        wishlistService.removeProductFromWishlist(customerId, productId);
        return ResponseEntity.ok("Product removed from wishlist successfully");
    }

    @DeleteMapping
    public ResponseEntity<String> clearWishlist(@PathVariable Long customerId) {
        wishlistService.clearWishlist(customerId);
        return ResponseEntity.ok("Wishlist cleared successfully");
    }
}
