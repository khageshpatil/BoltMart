package com.boltomart.customer_service.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "customer", indexes = {@Index(name = "idx_customer_phone_number", columnList = "phone_number")})
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "img_url")
    private String imgUrl;

    @Column(name = "phone_number",unique = true)
    private String phoneNumber;

    @Column(name = "email", unique = true)
    private String email;


    @OneToMany(mappedBy = "customer")
    @ToString.Exclude
    private List<Address> addresses;

    @OneToMany(mappedBy = "customer")
    @ToString.Exclude
    private List<Session> sessions;

    @OneToMany(mappedBy = "customer")
    private List<Order> orders;

    @OneToOne(mappedBy = "customer")
    @JsonIgnore
    @ToString.Exclude
    private Cart cart;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
