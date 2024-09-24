package com.boltomart.auth_service.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponse {

	private boolean status;
    private String jwt;
    private String refreshToken;
    private String message;

}
