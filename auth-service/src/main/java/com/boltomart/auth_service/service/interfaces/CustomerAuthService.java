package com.boltomart.auth_service.service.interfaces;

import com.boltomart.auth_service.dto.AuthDto;
import com.boltomart.auth_service.exception.UserException;

import com.boltomart.auth_service.response.UserAuthResponse;


public interface CustomerAuthService {

	UserAuthResponse authSignup(AuthDto authDto) throws  UserException;

	UserAuthResponse customerAuthLogin(AuthDto authDto) throws UserException;

	boolean logoutAll(String phoneNumber) throws UserException;

	boolean logout(String phoneNumber,String device) throws UserException;




}
