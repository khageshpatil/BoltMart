package com.boltomart.customer_service.service;

import com.boltomart.customer_service.dto.ProductWishlistDto;
import com.boltomart.customer_service.dto.ShopWishlistDto;
import com.boltomart.customer_service.entity.Customer;
import com.boltomart.customer_service.entity.Product;
import com.boltomart.customer_service.entity.Vendor;
import com.boltomart.customer_service.entity.Wishlist;
import com.boltomart.customer_service.repository.CustomerRepository;
import com.boltomart.customer_service.repository.ProductRepository;
import com.boltomart.customer_service.repository.VendorRepository;
import com.boltomart.customer_service.repository.WishlistRepository;
import com.boltomart.customer_service.response.WishlistProductResponse;
import com.boltomart.customer_service.response.WishlistShopResponse;
import com.boltomart.customer_service.service.interfaces.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class WishlistServiceImpl implements WishlistService {
    @Autowired
    private WishlistRepository wishlistRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private VendorRepository vendorRepository;
    @Override
    public String addProductToWishlist(ProductWishlistDto productWishlistDto) {
        if(wishlistRepository.findByCustomerIdAndProductId(productWishlistDto.getCustomerId(), productWishlistDto.getProductId())!=null){
            throw new RuntimeException("Product already added");
        }

        Wishlist wishlist=new Wishlist();
        Product product = productRepository.findById(productWishlistDto.getProductId()).orElseThrow(() -> new RuntimeException("Product not found"));
        Customer customer = customerRepository.findById(productWishlistDto.getCustomerId()).orElseThrow(() -> new RuntimeException("Customer not found"));
        wishlist.setProduct(product);
        wishlist.setCustomer(customer);
        wishlist.setCreatedAt(LocalDateTime.now());
        wishlistRepository.save(wishlist);

        return "Product added to wishlist";

    }

    @Override
    public List<WishlistProductResponse> getProductWishlistByCustomerId(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new RuntimeException("Customer not found"));
        return wishlistRepository.findAllProductsByCustomerId(customerId);
    }
    @Transactional
    @Override
    public void removeProductFromWishlist(Long customerId, Long productId) {
        wishlistRepository.deleteByCustomerIdAndProductId(customerId, productId);
    }

    @Transactional
    @Override
    public void clearWishlist(Long customerId) {
        wishlistRepository.deleteAllByCustomerId(customerId);
    }

    @Override
    public List<WishlistShopResponse> getShopWishlistByCustomerId(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new RuntimeException("Customer not found"));
        return wishlistRepository.findAllShopsByCustomerId(customerId);
    }

    @Override
    public String addShopToWishlist(ShopWishlistDto shopWishlistDto) {
        if(wishlistRepository.findByCustomerIdAndVendorId(shopWishlistDto.getCustomerId(), shopWishlistDto.getVendorId())!=null){
            throw new RuntimeException("Vendor already added");
        }

        Wishlist wishlist=new Wishlist();
        Vendor vendor = vendorRepository.findById(shopWishlistDto.getVendorId()).orElseThrow(() -> new RuntimeException("Product not found"));
        Customer customer = customerRepository.findById(shopWishlistDto.getCustomerId()).orElseThrow(() -> new RuntimeException("Customer not found"));
        wishlist.setVendor(vendor);
        wishlist.setCustomer(customer);
        wishlist.setCreatedAt(LocalDateTime.now());
        wishlistRepository.save(wishlist);

        return "Shop added to wishlist";
    }
    @Transactional
    @Override
    public void removeVendorFromWishlist(Long customerId, Long vendorId) {
        wishlistRepository.deleteByCustomerIdAndVendorId(customerId, vendorId);
        }
}

