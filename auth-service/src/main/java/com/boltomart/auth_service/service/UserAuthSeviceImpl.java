package com.boltomart.auth_service.service;


import com.boltomart.auth_service.config.JwtTokenProvider;
import com.boltomart.auth_service.dto.AuthDto;
import com.boltomart.auth_service.dto.RefreshTokenRequest;
import com.boltomart.auth_service.entity.Customer;
import com.boltomart.auth_service.entity.Vendor;
import com.boltomart.auth_service.repository.CustomerRepository;
import com.boltomart.auth_service.repository.VendorRepository;
import com.boltomart.auth_service.response.AuthResponse;
import com.boltomart.auth_service.response.UserAuthResponse;
import com.boltomart.auth_service.service.interfaces.CustomerAuthService;
import com.boltomart.auth_service.service.interfaces.UserAuthService;
import com.boltomart.auth_service.service.interfaces.VendorAuthService;
import com.boltomart.auth_service.validation.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;


import com.boltomart.auth_service.exception.UserException;


@Service
public class UserAuthSeviceImpl implements UserAuthService {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private CustomerAuthService customerAuthService;
    @Autowired
    private VendorAuthService vendorAuthService;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private VendorRepository vendorRepository;
    @Autowired
    private Validate validate;

    @Override
    public UserAuthResponse authLogin(AuthDto authDto) throws UserException {
        String phoneNumber = authDto.getPhoneNumber();
        validate.phoneNumber(phoneNumber);
        Customer customer = customerRepository.findByPhoneNumber(phoneNumber);
        if (customer != null) {
            return customerAuthService.customerAuthLogin(authDto);
        }
        Vendor vendor = vendorRepository.findByPhoneNumber(phoneNumber);
        if (vendor != null) {
            return vendorAuthService.vendorAuthLogin(authDto);
        } else {
            throw new UserException("You need to signup !!");
        }
    }
    @Override
    public AuthResponse refreshAccessToken(RefreshTokenRequest refreshTokenRequest) throws UserException {
        String refreshToken = refreshTokenRequest.getRefreshToken();

        if (jwtTokenProvider.validateToken(refreshToken)) {
            String email = jwtTokenProvider.getEmailFromJwtToken(refreshToken);
            Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, AuthorityUtils.NO_AUTHORITIES);

            String newAccessToken = jwtTokenProvider.generateAccessToken(authentication);
            return toAuthResponse(newAccessToken,refreshToken);
        } else {
            throw new UserException("Error validating refresh token.");
        }
    }
    AuthResponse toAuthResponse(String accessToken,String refreshToken){
        AuthResponse authResponse = new AuthResponse();
        authResponse.setMessage("Access Token updated successfuly");
        authResponse.setJwt(accessToken);
        authResponse.setRefreshToken(refreshToken);
        authResponse.setStatus(true);
        return authResponse;}
}






