package com.boltomart.auth_service.service.interfaces;

import com.boltomart.auth_service.dto.AuthDto;
import com.boltomart.auth_service.dto.RefreshTokenRequest;
import com.boltomart.auth_service.exception.UserException;
import com.boltomart.auth_service.response.AuthResponse;
import com.boltomart.auth_service.response.UserAuthResponse;

public interface UserAuthService {
    UserAuthResponse authLogin(AuthDto authDto) throws UserException;

    AuthResponse refreshAccessToken(RefreshTokenRequest refreshTokenRequest) throws UserException;


}
