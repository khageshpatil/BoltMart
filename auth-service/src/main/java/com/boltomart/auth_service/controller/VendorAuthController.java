package com.boltomart.auth_service.controller;

import com.boltomart.auth_service.dto.VendorRequestDTO;

import com.boltomart.auth_service.exception.UserException;

import com.boltomart.auth_service.response.UserAuthResponse;
import com.boltomart.auth_service.service.interfaces.CustomerAuthService;
import com.boltomart.auth_service.service.interfaces.VendorAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin("*")
@RestController
@RequestMapping("/api/vendor")
public class VendorAuthController {

    @Autowired
    private VendorAuthService vendorAuthService;

    @Autowired
    private CustomerAuthService customerAuthService;

    @PostMapping("/signup")
    public ResponseEntity<UserAuthResponse> signUpVendor (@RequestBody VendorRequestDTO vendorRequestDTO)throws UserException {

            UserAuthResponse response = vendorAuthService.registerVendor(vendorRequestDTO);
            return ResponseEntity.ok(response);

    }
    @PostMapping("/logoutAll")
    public ResponseEntity<String> logoutAll(@RequestParam String phoneNumber) throws UserException {
        boolean result = vendorAuthService.logoutAll(phoneNumber);
        if (result) {
            return new ResponseEntity<>("User logged out successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Logout failed: User not found or already logged out", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestParam String phoneNumber,@RequestParam String device) throws UserException {
        boolean result = vendorAuthService.logout(phoneNumber,device);
        if (result) {
            return new ResponseEntity<>("User logged out successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Logout failed: User not found or already logged out", HttpStatus.BAD_REQUEST);
        }
    }



}
