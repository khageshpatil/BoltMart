package com.boltomart.productservice.entity;


import com.boltomart.productservice.enums.MediaType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "product_media", indexes = {@Index(name = "idx_product_images_product_id", columnList = "product_id")})
@NoArgsConstructor
@AllArgsConstructor
public class ProductMedia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "img_url")
    private String imgUrl;

    @Column(name = "img_hash")
    private String imgHash;

    @Enumerated(EnumType.STRING)
    @Column(name = "img_type")
    private MediaType imgType;

    @Column(name = "status")
    private Integer status;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}

