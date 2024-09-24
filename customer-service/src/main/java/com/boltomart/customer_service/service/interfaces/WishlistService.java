package com.boltomart.customer_service.service.interfaces;

import com.boltomart.customer_service.dto.ProductWishlistDto;
import com.boltomart.customer_service.dto.ShopWishlistDto;
import com.boltomart.customer_service.response.WishlistProductResponse;
import com.boltomart.customer_service.response.WishlistShopResponse;

import java.util.List;

public interface WishlistService {
    String addProductToWishlist(ProductWishlistDto productWishlistDto);

    List<WishlistProductResponse> getProductWishlistByCustomerId(Long customerId);

    void removeProductFromWishlist(Long customerId, Long productId);

    void clearWishlist(Long customerId);

    List<WishlistShopResponse> getShopWishlistByCustomerId(Long customerId);

    String addShopToWishlist(ShopWishlistDto shopWishlistDto);

    void removeVendorFromWishlist(Long customerId, Long vendorId);
}
