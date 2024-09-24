package com.boltomart.auth_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshTokenRequest {

    private String refreshToken;
    public String getRefreshToken() {
        return refreshToken;
    }

}
