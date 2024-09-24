package com.boltomart.cart_service.service.interfaces;

import com.boltomart.cart_service.dto.CartDto;
import com.boltomart.cart_service.dto.CartItemDto;
import com.boltomart.cart_service.exception.CartException;
import com.boltomart.cart_service.response.AddToCartResponse;
import com.boltomart.cart_service.response.CartResponse;

public interface CartService {

    AddToCartResponse addToCart(CartDto cartDto) throws CartException;

    void updateCartItem(Long customerId, Long productId,Long productVariationId, int quantity)throws CartException;

    void removeCartItem(Long customerId,Long productId, Long productVariationId)throws CartException;

    CartResponse getCart(Long customerId)throws CartException;
}