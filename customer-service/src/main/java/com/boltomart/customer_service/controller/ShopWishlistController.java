package com.boltomart.customer_service.controller;

import com.boltomart.customer_service.dto.ShopWishlistDto;
import com.boltomart.customer_service.response.WishlistShopResponse;
import com.boltomart.customer_service.service.interfaces.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers/{customerId}/wishlist/shops")
public class ShopWishlistController {

    @Autowired
    private WishlistService wishlistService;

    @PostMapping()
    public ResponseEntity<String> addShopToWishlist(@PathVariable Long customerId, @RequestBody ShopWishlistDto shopWishlistDto) {
        shopWishlistDto.setCustomerId(customerId);
        String message = wishlistService.addShopToWishlist(shopWishlistDto);
        return ResponseEntity.ok(message);
    }

    @GetMapping()
    public ResponseEntity<List<WishlistShopResponse>> getWishlistOfShops(@PathVariable Long customerId) {
        List<WishlistShopResponse> wishlists =wishlistService.getShopWishlistByCustomerId(customerId);
        return ResponseEntity.ok(wishlists);
    }

    @DeleteMapping("/{vendorId}")
    public ResponseEntity<String> removeVendorFromWishlist(@PathVariable Long customerId, @PathVariable Long vendorId) {
        wishlistService.removeVendorFromWishlist(customerId, vendorId);
        return ResponseEntity.ok("Shop removed from wishlist successfully");
    }
}
