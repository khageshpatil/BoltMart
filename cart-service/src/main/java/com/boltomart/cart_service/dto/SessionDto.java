package com.boltomart.cart_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionDto {

    private Long id;
    private Long customerId; // To link it with Customer
    private String device;
    private String ipAddress;
    private LocalDateTime loginTime;
    private LocalDateTime logoutTime;
    private boolean isLoggedIn;
    private Integer status;
}
