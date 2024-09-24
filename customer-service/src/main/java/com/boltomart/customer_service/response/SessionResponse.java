package com.boltomart.customer_service.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.service.annotation.GetExchange;

import java.time.LocalDateTime;
@Data
@Getter
@Setter
public class SessionResponse {
    private Long id;
    private Long customerId; // To link it with Customer
    private String device;
    private String ipAddress;
    private LocalDateTime loginTime;
    private LocalDateTime logoutTime;
    private boolean isLogged;
    private Integer status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
