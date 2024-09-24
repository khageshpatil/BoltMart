package com.boltomart.productservice.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "vendor", indexes = {@Index(name = "idx_vendor_phone_number", columnList = "phone_number")})
@NoArgsConstructor
@AllArgsConstructor
public class Vendor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vendor_name")
    private String vendorName;

    @Column(name = "logo")
    private String logo;

    @Column(name = "shop_name")
    private String shopName;

    @Column(name = "gst_number")
    private String gstNumber;

    @Column(name = "phone_number")
    private String phoneNumber;


    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;


    @OneToMany(mappedBy = "vendor")
    private List<Product> products;

    @OneToMany(mappedBy = "vendor")
    private List<Session> sessions;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name="rating")
    private Float rating;
}
