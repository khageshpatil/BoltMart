package com.boltomart.cart_service.service;

import com.boltomart.cart_service.constants.ApplicationConstant;
import com.boltomart.cart_service.dto.CartDto;
import com.boltomart.cart_service.dto.CartItemDto;
import com.boltomart.cart_service.entity.*;
import com.boltomart.cart_service.exception.CartException;
import com.boltomart.cart_service.helper.Helper;
import com.boltomart.cart_service.repository.CartItemsRepository;
import com.boltomart.cart_service.repository.CartRepository;
import com.boltomart.cart_service.repository.ProductRepository;
import com.boltomart.cart_service.repository.ProductVariationRepository;
import com.boltomart.cart_service.response.AddToCartResponse;
import com.boltomart.cart_service.response.CartResponse;
import com.boltomart.cart_service.service.interfaces.CartService;
import com.boltomart.cart_service.validate.Validate;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Async
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemsRepository cartItemsRepository;
    @Autowired
    private ProductVariationRepository productVariationRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private Validate validate;

    @Autowired
    private Helper helper;

    private static final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

    @Override
    @Transactional
    public AddToCartResponse addToCart(CartDto cartDto) throws CartException {
        logger.debug("Fetching customer with ID {}", cartDto.getCustomerId());
        Customer customer = validate.getCustomerById(cartDto.getCustomerId());

        logger.debug("Finding or creating cart for customer ID {}", customer.getId());
        Cart cart = helper.findOrCreateCartForCustomer(customer, ApplicationConstant.ACTIVE);

        if (cartDto.getCartItems() == null) {
            throw new CartException("Cart items cannot be null.");
        }
        CartItemDto cartItemDto = cartDto.getCartItems();
        ProductVariation productVariation = null;
        Product product = null;

        // Fetch product and variation
        Optional<Product> productOptional = productRepository.findById(cartItemDto.getProductId());
        if (productOptional.isPresent()) {
            product = productOptional.get();
            boolean hasVariation = validate.hasProductVariation(product.getId());

            if (hasVariation) {
                if(cartItemDto.getProductVariationId()==null){
                    throw new CartException("Product Variation can not be null for specific Product id");
                }
                validate.validateProductVariation(cartItemDto.getProductVariationId());
                Optional<ProductVariation> productVariationOptional = productVariationRepository.findById(cartItemDto.getProductVariationId());
                if (productVariationOptional.isPresent()) {
                    productVariation = productVariationOptional.get();
                    if(productVariation.getProduct()==null){
                        throw new CartException("Product for Product Variation can not be null");
                    }
                    if (!productVariation.getProduct().getId().equals(product.getId())) {
                        throw new CartException("Product does not belong to Product Variation");
                    }
                }
            }
        }

        if (product == null) {
            throw new CartException("Product not found.");
        }
        // Validate Product Quantity
        validate.quantity(cartItemDto.getQuantity(), product,productVariation);
        // Checking for Vendor
        if (!helper.isSameVendor(cart, product)) {
            // If vendor mismatch, clear existing items for the mismatched vendor
            logger.debug("Vendor mismatch detected. Clearing existing cart items.");
            cart.getCartItems().removeIf(cartItem -> !helper.isSameVendor(cart, cartItem.getProduct()));
            cartItemsRepository.deleteAll(cart.getCartItems());
            logger.debug("Creating new cart item for product variation ID {}", cartDto.getCartItems().getProductVariationId());
            CartItems newCartItem = helper.handleNewCartItem(cart, cartDto.getCartItems(),product,productVariation);
            cartItemsRepository.save(newCartItem);
        }
        else{
            CartItems existingCartItem = helper.findExistingCartItem(cart, product,productVariation);
            if (existingCartItem != null){
                existingCartItem =  helper.handleExistingCartItem(existingCartItem, cartDto.getCartItems(),product,productVariation);
                cartItemsRepository.save(existingCartItem);
            }
            else {
                logger.debug("Creating new cart item for product variation ID {}", cartDto.getCartItems().getProductVariationId());
                CartItems newCartItem = helper.handleNewCartItem(cart, cartDto.getCartItems(),product, productVariation);
                cartItemsRepository.save(newCartItem);
            }
        }
        logger.debug("Saving cart for customer ID {}", customer.getId());
        cartRepository.save(cart);
        System.out.println("Error END");
        return helper.buildAddToCartResponse(cartDto);
    }

    public void updateCartItem(Long customerId, Long productId,Long productVariationId, int quantity) throws CartException {
        validate.getCustomerById(customerId);

        // Fetch the cart by customerId
        Cart cart = cartRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new CartException("Cart not found for the customer."));
        ProductVariation productVariation = null;
        Product product = null;
        // Fetch product and variation
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isPresent()) {
            product = productOptional.get();
            boolean hasVariation = validate.hasProductVariation(product.getId());
            if (hasVariation) {
                if(productVariationId==null){
                    throw new CartException("Product Variation can not be null for specific Product id");
                }
                validate.validateProductVariation(productVariationId);
                Optional<ProductVariation> productVariationOptional = productVariationRepository.findById(productVariationId);
                if (productVariationOptional.isPresent()) {
                    productVariation = productVariationOptional.get();
                    if(productVariation.getProduct()==null){
                        throw new CartException("Product for Product Variation can not be null");
                    }
                    if (!productVariation.getProduct().getId().equals(product.getId())) {
                        throw new CartException("Product does not belong to Product Variation");
                    }
                }
            }
        }

        if (product == null) {
            throw new CartException("Product not found.");
        }
        CartItems cartItem = null;
        // Find the cart item by product variation ID and cart ID
        if(productVariation!=null) {
            cartItem = cartItemsRepository.findByProductVariationIdAndCartId(productVariationId, cart.getId())
                    .orElseThrow(() -> new CartException("Cart item not found."));
        }
        else{
            cartItem = cartItemsRepository.findByProductIdAndCartId(productId,cart.getId())
                    .orElseThrow(() -> new CartException("Cart item not found."));
        }

        if (!cartItem.getCart().getCustomer().getId().equals(customerId)) {
            throw new CartException("Cart item does not belong to the specified customer.");
        }

        validate.quantity(quantity,product,productVariation); // Pass an appropriate CartItemDto if needed

        cartItem.setQuantity(quantity);
        if(productVariation!=null) {
            cartItem.setTotalPrice(productVariation.getUnitPrice().multiply(BigDecimal.valueOf(quantity)));
        }else{
            cartItem.setTotalPrice(product.getUnitPrice().multiply(BigDecimal.valueOf(quantity)));
        }
        cartItemsRepository.save(cartItem);
    }

    @Transactional
    public void removeCartItem(Long customerId,Long productId, Long productVariationId) throws CartException {
        // Validate that the customer exists
        validate.getCustomerById(customerId);

        // Fetch the cart for the customer
        Cart cart = cartRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new CartException("Cart not found for the customer."));

        ProductVariation productVariation = null;
        Product product = null;
        // Fetch product and variation
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isPresent()) {
            product = productOptional.get();
            boolean hasVariation = validate.hasProductVariation(product.getId());

            if (hasVariation) {
                if(productVariationId==null){
                    throw new CartException("Product Variation can not be null for specific Product id");
                }
                validate.validateProductVariation(productVariationId);
                Optional<ProductVariation> productVariationOptional = productVariationRepository.findById(productVariationId);
                if (productVariationOptional.isPresent()) {
                    productVariation = productVariationOptional.get();
                    if(productVariation.getProduct()==null){
                        throw new CartException("Product for Product Variation can not be null");
                    }
                    if (!productVariation.getProduct().getId().equals(product.getId())) {
                        throw new CartException("Product does not belong to Product Variation");
                    }
                }
            }
        }

        if (product == null) {
            throw new CartException("Product not found.");
        }

        CartItems cartItem = null;
        // Find the cart item by product variation ID and cart ID
        if(productVariation!=null) {
            cartItem = cartItemsRepository.findByProductVariationIdAndCartId(productVariationId, cart.getId())
                    .orElseThrow(() -> new CartException("Cart item not found."));
        }
        else{
            cartItem = cartItemsRepository.findByProductIdAndCartId(productId,cart.getId())
                    .orElseThrow(() -> new CartException("Cart item not found."));
        }

        // Ensure the cart item belongs to the specified customer
        if (!cartItem.getCart().getCustomer().getId().equals(customerId)) {
            throw new CartException("Cart item does not belong to the specified customer.");
        }

        // Remove the cart item
        try {
            cartItemsRepository.delete(cartItem);
        } catch (Exception e) {
            // Handle any exceptions that may occur during the deletion
            throw new CartException("Error occurred while removing cart item.", e);
        }

        // Refresh the cart to get the updated state
        cart = cartRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new CartException("Cart not found after item removal."));

        // Get the current cart items
        List<CartItems> currentCartItems = cartItemsRepository.findByCartId(cart.getId());

        // Check if the cart is empty
        if (currentCartItems.isEmpty()) {
            // Delete the cart
            System.out.println("-----------------------------");
            cartRepository.delete(cart);
        }
    }

    public CartResponse getCart(Long customerId) throws CartException {
        validate.getCustomerById(customerId);

        Cart cart = cartRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new CartException("Cart not found for the customer."));

        return helper.buildCartResponse(cart);
    }
}