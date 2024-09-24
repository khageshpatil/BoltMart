package com.boltomart.cart_service.controller;

import com.boltomart.cart_service.dto.CartDto;
import com.boltomart.cart_service.dto.CartItemDto;
import com.boltomart.cart_service.exception.CartException;
import com.boltomart.cart_service.response.AddToCartResponse;
import com.boltomart.cart_service.response.ApiResponse;
import com.boltomart.cart_service.response.CartResponse;
import com.boltomart.cart_service.service.interfaces.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin("*")
@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @PostMapping("/add-to-cart")
    public ResponseEntity<AddToCartResponse> addToCart(@RequestBody CartDto cartDto) throws CartException {
        AddToCartResponse addToCartResponse = cartService.addToCart(cartDto);
        return new ResponseEntity<>(addToCartResponse, HttpStatus.OK);
    }
    @PutMapping("/{customer_id}/update-cart-item/{product_id}")
    public ResponseEntity<ApiResponse> updateCartItem(@PathVariable Long customer_id, @PathVariable("product_id") Long product_id, @RequestParam(value = "product_variation_id", required = false) Long product_variation_id, @RequestParam int quantity) throws CartException {
        cartService.updateCartItem(customer_id,product_id, product_variation_id, quantity);
        ApiResponse response = new ApiResponse("Cart Item updated successfully", true);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @DeleteMapping("/{customer_id}/remove-cart-item/{product_id}")
    public ResponseEntity<ApiResponse> removeCartItem(
            @PathVariable Long customer_id,
            @PathVariable("product_id") Long product_id,
            @RequestParam(value = "product_variation_id", required = false) Long product_variation_id)
            throws CartException {
        cartService.removeCartItem(customer_id, product_id, product_variation_id);
        ApiResponse response = new ApiResponse("Cart Item deleted successfully", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{customer_id}/get-cart")
    public ResponseEntity<CartResponse> getCart(@PathVariable Long customer_id) throws CartException{
        CartResponse cartResponse = cartService.getCart(customer_id);
        return new ResponseEntity<>(cartResponse,HttpStatus.OK);
    }


}