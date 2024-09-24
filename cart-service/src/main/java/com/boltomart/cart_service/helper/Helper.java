package com.boltomart.cart_service.helper;

import com.boltomart.cart_service.constants.ApplicationConstant;
import com.boltomart.cart_service.dto.CartDto;
import com.boltomart.cart_service.dto.CartItemDto;
import com.boltomart.cart_service.entity.*;
import com.boltomart.cart_service.exception.CartException;
import com.boltomart.cart_service.repository.CartItemsRepository;
import com.boltomart.cart_service.repository.CartRepository;
import com.boltomart.cart_service.repository.ProductRepository;
import com.boltomart.cart_service.repository.ProductVariationRepository;
import com.boltomart.cart_service.response.AddToCartResponse;
import com.boltomart.cart_service.response.CartItemResponse;
import com.boltomart.cart_service.response.CartResponse;
import com.boltomart.cart_service.validate.Validate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class Helper {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemsRepository cartItemsRepository;

    @Autowired
    private ProductVariationRepository productVariationRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private Validate validate;

    @Autowired
    private ModelMapper modelMapper;

    public boolean isSameVendor(Cart cart, Product product) {
        if (cart.getCartItems().isEmpty()) {
            return true; // An empty cart can accept any vendor
        }

        Product existingCartProduct = cart.getCartItems().get(0).getProduct();
        Long existingCartItemVendorId = existingCartProduct.getVendor().getId();
        Long newProductVendorId = product.getVendor().getId();
        return existingCartItemVendorId.equals(newProductVendorId);
    }

    public CartItems findExistingCartItem(Cart cart, Product product, ProductVariation productVariation) {
        return cart.getCartItems().stream()
                .filter(item -> {
                    if (productVariation == null) {
                        // Match based on product if productVariation is null
                        return item.getProductVariation() == null
                                || item.getProductVariation().getProduct().equals(product);
                    } else {
                        // Match based on productVariation if it is not null
                        return item.getProductVariation() != null && item.getProductVariation().equals(productVariation);
                    }
                })
                .findFirst()
                .orElse(null);
    }



    public CartItems handleNewCartItem(Cart cart, CartItemDto cartItemDto, Product product, ProductVariation productVariation) {

        CartItems newCartItem = new CartItems();
        newCartItem.setCart(cart);
        newCartItem.setProduct(product);
        newCartItem.setProductVariation(productVariation);
        newCartItem.setStatus(ApplicationConstant.ACTIVE);
        newCartItem.setQuantity(cartItemDto.getQuantity());
        if(productVariation!=null) {
            newCartItem.setUnitPrice(productVariation.getUnitPrice());
            newCartItem.setTotalPrice(productVariation.getUnitPrice().multiply(BigDecimal.valueOf(cartItemDto.getQuantity())));
        }else{
            newCartItem.setUnitPrice(product.getUnitPrice());
            newCartItem.setTotalPrice(product.getUnitPrice().multiply(BigDecimal.valueOf(cartItemDto.getQuantity())));
        }
        cart.getCartItems().add(newCartItem);
        return newCartItem;
    }

    public Cart findOrCreateCartForCustomer(Customer customer, int status) {
        Optional<Cart> optionalCart = cartRepository.findByCustomerIdAndStatus(customer.getId(), status);
        return optionalCart.orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setCustomer(customer);
            newCart.setStatus(status);
            return cartRepository.save(newCart);
        });
    }
    public CartResponse buildCartResponse(Cart cart) {

        List<CartItemResponse> cartItemResponses = cart.getCartItems().stream()
                .map(cartItem -> {
                    CartItemResponse response = modelMapper.map(cartItem, CartItemResponse.class);
                    if(cartItem.getProductVariation()!=null) {
                        response.setProductVariationId(cartItem.getProductVariation().getId());
                        response.setProductVariationName(cartItem.getProductVariation().getVariationName());
                        if(!cartItem.getProductVariation().getProduct().getImages().isEmpty()) {
                            response.setImgUrl(cartItem.getProductVariation().getProduct().getImages().get(0).getImgUrl());
                        }
                    }
                    else{
                        if(!cartItem.getProduct().getImages().isEmpty()) {
                            response.setImgUrl(cartItem.getProduct().getImages().get(0).getImgUrl());
                        }
                    }
                    response.setProductId(cartItem.getProduct().getId());
                    response.setProductName(cartItem.getProduct().getProductName());
                    return response;
                })
                .collect(Collectors.toList());

        BigDecimal totalPrice = cartItemResponses.stream()
                .map(CartItemResponse::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        modelMapper.map(cart, CartResponse.class);
        CartResponse cartResponse = new CartResponse();
        cartResponse.setCartId(cart.getId());
        cartResponse.setCustomerId(cart.getCustomer().getId());
        cartResponse.setCartItems(cartItemResponses);
        cartResponse.setTotalPrice(totalPrice);

        return cartResponse;
    }

    public CartItems handleExistingCartItem(CartItems existingCartItem,CartItemDto cartItemDto, Product product, ProductVariation productVariation) {
        int newQuantity = existingCartItem.getQuantity() + cartItemDto.getQuantity();
        existingCartItem.setQuantity(newQuantity);

        BigDecimal unitPrice;
        if (productVariation != null) {
            unitPrice = productVariation.getUnitPrice();
        } else {
            unitPrice = product.getUnitPrice();
        }

        existingCartItem.setTotalPrice(unitPrice.multiply(BigDecimal.valueOf(newQuantity)));
        return existingCartItem;
    }

    public AddToCartResponse buildAddToCartResponse(CartDto cartDto) throws CartException {
        // Extract cart item details
        CartItemDto cartItemDto = cartDto.getCartItems();

        // Fetch cart and product details
        Cart cart = cartRepository.findByCustomerId(cartDto.getCustomerId())
                .orElseThrow(() -> new CartException("Cart not found with customerId: " + cartDto.getCustomerId()));

        Product product = productRepository.findById(cartItemDto.getProductId())
                .orElseThrow(() -> new CartException("Product not found with ID: " + cartItemDto.getProductId()));

        // Determine if the product has a variation
        ProductVariation productVariation = null;
        if (cartItemDto.getProductVariationId() != null ) {
            productVariation = productVariationRepository.findById(cartItemDto.getProductVariationId())
                    .orElse(null); // If not found, productVariation will be null
            if(productVariation!=null && productVariation.getProduct()!=product){
                throw new CartException("Product variation do not belong to Product");
            }
        }

        // Build AddToCartResponse
        AddToCartResponse response = new AddToCartResponse();
        response.setCartId(cart.getId());  // Assuming cartDto has an ID field
        response.setCustomerId(cartDto.getCustomerId());  // Assuming cartDto has a customerId field
        response.setProductId(cartItemDto.getProductId());

        // Set Product Name
        response.setProductName(product.getProductName());

        // Set Product Variation details if present
        if (productVariation != null && cartItemDto.getProductId().equals(productVariation.getProduct().getId())) {
            response.setProductVariationId(cartItemDto.getProductVariationId());
            response.setProductVariationName(productVariation.getVariationName());
            response.setProductVariationValue(productVariation.getVariationValue());
            if(!productVariation.getProduct().getImages().isEmpty()){
                response.setImgUrl(productVariation.getProduct().getImages().get(0).getImgUrl());
            }
        } else {
            response.setProductVariationName(null);
            response.setProductVariationValue(null);
            if (!product.getImages().isEmpty()) {
                response.setImgUrl(product.getImages().get(0).getImgUrl());
            }
        }

        // Set Quantity and Prices
        BigDecimal unitPrice = productVariation != null ? productVariation.getUnitPrice() : product.getUnitPrice();
        response.setUnitPrice(unitPrice);
        response.setTotalPrice(unitPrice.multiply(BigDecimal.valueOf(cartItemDto.getQuantity())));
        response.setQuantity(cartItemDto.getQuantity());

        return response;
    }


}
