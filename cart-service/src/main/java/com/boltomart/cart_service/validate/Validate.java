package com.boltomart.cart_service.validate;

import com.boltomart.cart_service.dto.CartDto;
import com.boltomart.cart_service.dto.CartItemDto;
import com.boltomart.cart_service.entity.Customer;
import com.boltomart.cart_service.entity.Product;
import com.boltomart.cart_service.entity.ProductVariation;
import com.boltomart.cart_service.exception.CartException;
import com.boltomart.cart_service.repository.CustomerRepository;
import com.boltomart.cart_service.repository.ProductRepository;
import com.boltomart.cart_service.repository.ProductVariationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class Validate {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductVariationRepository productVariationRepository;

    public Customer getCustomerById(Long customerId) throws CartException {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new CartException("Customer not found with id: " + customerId));
    }

    public void validateProductVariation(Long productVariationId) throws CartException {
        if(productVariationId<=0){
            throw new CartException("ProductVariationId should be positive");
        }
         productVariationRepository.findById(productVariationId)
                .orElseThrow(() -> new CartException("Product variation not found with id: " + productVariationId));
    }

    public boolean hasProductVariation(Long productId) throws CartException {
        if(productId<=0){
            throw new CartException("ProductVariationId should be positive");
        }
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new CartException("Product not found with ID " + productId));
        return product.isHasVariations();
    }
    public void quantity(int quantity, Product product, ProductVariation productVariation) throws CartException {
        if (quantity<= 0) {
            throw new CartException("Invalid cart item: quantity must be positive.");
        }

        int stock;

        if(productVariation!=null){
            stock = productVariation.getStock();
        }
        else{
            stock = product.getStock();
        }
        if (quantity > stock) {
            throw new CartException("Quantity should be less than or equal to the stock present.");
        }
    }
}