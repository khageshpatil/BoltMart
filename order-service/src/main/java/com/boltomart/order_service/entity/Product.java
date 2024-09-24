package com.boltomart.order_service.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "products", indexes = {@Index(name = "idx_products_vendor_id", columnList = "vendor_id"), @Index(name = "idx_products_category_id", columnList = "category_id")})
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;

    @Column(name = "SKU")
    private String SKU;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_description")
    private String productDescription;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "has_variations")
    private boolean hasVariations;

    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    @Column(name = "stock")
    private Integer stock;

    @Column(name = "size")
    private String size;

    @Column(name = "notes")
    private String notes;

    @Column(name = "status")
    private Integer status;


    @OneToMany(mappedBy = "product")
    private List<ProductVariation> variations;

    @OneToMany(mappedBy = "product")
    private List<ProductMedia> images;

    @OneToMany(mappedBy = "product")
    private List<ProductReview> productReviews;

    @Column(name="rating")
    private Float rating;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}