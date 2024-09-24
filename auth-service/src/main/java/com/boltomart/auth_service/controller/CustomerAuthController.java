package com.boltomart.auth_service.controller;

import com.boltomart.auth_service.response.UserAuthResponse;

import com.boltomart.auth_service.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.boltomart.auth_service.dto.AuthDto;

import com.boltomart.auth_service.exception.UserException;
import com.boltomart.auth_service.service.interfaces.CustomerAuthService;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/customer")
public class CustomerAuthController {

    @Autowired
    private CustomerAuthService customerAuthService;

    @Autowired
    private CustomerService customerService;

    @PostMapping("/signup")
    public ResponseEntity<UserAuthResponse> userAuth(@RequestBody AuthDto authDto) throws UserException {
        try {
            UserAuthResponse response = customerAuthService.authSignup(authDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (UserException e) {
            throw e;
        }
    }
        @PostMapping("/logoutAll")
        public ResponseEntity<String> logoutAll(@RequestParam String phoneNumber) throws UserException {
            boolean result = customerAuthService.logoutAll(phoneNumber);
            if (result) {
                return new ResponseEntity<>("User logged out successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Logout failed: User not found or already logged out", HttpStatus.BAD_REQUEST);
            }
        }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestParam String phoneNumber,@RequestParam String device) throws UserException {
        boolean result = customerAuthService.logout(phoneNumber,device);
        if (result) {
            return new ResponseEntity<>("User logged out successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Logout failed: User not found or already logged out", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/find")
    public ResponseEntity<Map<String, Object>> findCustomer(@RequestParam String phoneNumber) {

            boolean result = customerService.isCustomerExists(phoneNumber);
            Map<String, Object> response = new HashMap<>();
            if (result){
                response.put("status",true);
                response.put("message", "Customer exists");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.put("status",false);
                response.put("message", "Customer does not exist");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

            }

    }








   

}
