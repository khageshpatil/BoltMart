package com.boltomart.customer_service.dto;

import com.boltomart.customer_service.enums.AddressType;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class AddressDto {

    private Long id;
    private Long customerId; // To link it with Customer
    private String addressLine1;
    private String addressLine2;
    private String city;
    private Integer pincode;
    private String state;
    private String phoneNumber;
    private AddressType addressType;
    private Integer status;
    private Double latitude;
    private Double longitude;

}