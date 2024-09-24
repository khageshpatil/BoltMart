package com.boltomart.auth_service.service.interfaces;

import com.boltomart.auth_service.dto.AuthDto;
import com.boltomart.auth_service.dto.VendorRequestDTO;
import com.boltomart.auth_service.exception.UserException;

import com.boltomart.auth_service.response.UserAuthResponse;


public interface VendorAuthService {

    UserAuthResponse registerVendor(VendorRequestDTO vendorRequestDTO) throws  UserException;

    boolean logoutAll(String phoneNumber) throws UserException;
    boolean logout(String phoneNumber,String device) throws UserException;

    UserAuthResponse vendorAuthLogin(AuthDto authDto) throws UserException;
}
