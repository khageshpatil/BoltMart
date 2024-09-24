package com.boltomart.customer_service.repository;

import com.boltomart.customer_service.entity.Customer;
import com.boltomart.customer_service.entity.Wishlist;
import com.boltomart.customer_service.response.WishlistProductResponse;
import com.boltomart.customer_service.response.WishlistShopResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WishlistRepository extends JpaRepository<Wishlist,Long> {
    @Query("SELECT new com.boltomart.customer_service.response.WishlistProductResponse(w.id, w.product.id, (SELECT img.imgUrl FROM ProductMedia img WHERE img.product.id = p.id AND img.imgType = com.boltomart.customer_service.enums.MediaType.MAIN), p.productName, p.unitPrice, p.rating) " +
            "FROM Wishlist w " +
            "JOIN w.product p " +
            "WHERE w.customer.id = :customerId ")
    List<WishlistProductResponse> findAllProductsByCustomerId(@Param("customerId") Long customerId);

    void deleteByCustomerIdAndProductId(Long customerId, Long productId);

    void deleteAllByCustomerId(Long customerId);

    Wishlist findByCustomerIdAndProductId(Long customerId,Long productId);

    Wishlist findByCustomerIdAndVendorId(Long customerId,Long productId);

    @Query("SELECT new com.boltomart.customer_service.response.WishlistShopResponse(w.id, w.vendor.id,w.vendor.logo, w.vendor.shopName, w.vendor.rating) " +
            "FROM Wishlist w " +
            "WHERE w.customer.id = :customerId ")
    List<WishlistShopResponse> findAllShopsByCustomerId(@Param("customerId") Long customerId);

    void deleteByCustomerIdAndVendorId(Long customerId, Long vendorId);
}
