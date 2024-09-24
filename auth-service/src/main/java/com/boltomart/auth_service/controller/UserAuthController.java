package com.boltomart.auth_service.controller;

import com.boltomart.auth_service.dto.AuthDto;
import com.boltomart.auth_service.dto.RefreshTokenRequest;
import com.boltomart.auth_service.exception.UserException;
import com.boltomart.auth_service.response.AuthResponse;
import com.boltomart.auth_service.response.UserAuthResponse;
import com.boltomart.auth_service.service.interfaces.CustomerAuthService;
import com.boltomart.auth_service.service.interfaces.UserAuthService;
import com.boltomart.auth_service.service.interfaces.VendorAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class UserAuthController {
    @Autowired
    private CustomerAuthService customerAuthService;
    @Autowired
    private UserAuthService userAuthService;
    @Autowired
    private VendorAuthService vendorAuthService;

    @PostMapping("/login")
    public ResponseEntity<UserAuthResponse> userAuth(@RequestBody AuthDto authDto) throws UserException {
            return new ResponseEntity<>(userAuthService.authLogin(authDto), HttpStatus.OK);
    }
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshAccessToken(@RequestBody RefreshTokenRequest refreshTokenRequest)throws UserException{
        return new ResponseEntity<>(userAuthService.refreshAccessToken(refreshTokenRequest),HttpStatus.OK);
    }

}
