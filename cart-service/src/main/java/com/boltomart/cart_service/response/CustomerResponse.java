package com.boltomart.cart_service.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Getter
@Setter
public class CustomerResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String imgUrl;
    private String phoneNumber;
    private String email;

    // Optional: You can include these fields if needed, otherwise remove them
    private List<AddressResponse> addresses;
    private List<SessionResponse> sessions;
}