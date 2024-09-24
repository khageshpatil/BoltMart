package com.boltomart.auth_service.service;

import com.boltomart.auth_service.config.JwtTokenProvider;
import com.boltomart.auth_service.constants.ApplicationConstant;
import com.boltomart.auth_service.dto.AuthDto;
import com.boltomart.auth_service.dto.VendorRequestDTO;
import com.boltomart.auth_service.entity.Address;
import com.boltomart.auth_service.entity.Customer;
import com.boltomart.auth_service.entity.Session;
import com.boltomart.auth_service.entity.Vendor;
import com.boltomart.auth_service.exception.UserException;
import com.boltomart.auth_service.repository.AddressRepository;
import com.boltomart.auth_service.repository.SessionRepository;
import com.boltomart.auth_service.repository.VendorRepository;
import com.boltomart.auth_service.response.UserAuthResponse;

import com.boltomart.auth_service.service.interfaces.VendorAuthService;

import com.boltomart.auth_service.validation.Validate;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class VendorAuthServiceImpl implements VendorAuthService {

    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private VendorRepository vendorRepository;
    @Autowired
    private VendorService vendorService;
    @Autowired
    private SessionServiceImpl sessionServiceImpl;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private  ModelMapper modelMapper;
    @Autowired
    private Validate validate;

    @Override
    public UserAuthResponse registerVendor(VendorRequestDTO vendorRequestDTO) throws UserException {
        String phoneNumber = vendorRequestDTO.getShopPhoneNumber();
        validate.phoneNumber(phoneNumber);

        // Check if the phone number is already registered
        Vendor isVendor = vendorRepository.findByPhoneNumber(phoneNumber);
        if (isVendor != null) {
            throw new UserException("Phone Number Is Already Used With Another Account");
        }

        // using model mapper
        Address address = modelMapper.map(vendorRequestDTO.getAddress(),Address.class);
        address.setStatus(ApplicationConstant.ACTIVE);
        addressRepository.save(address);

        // Create new Vendor entity
        Vendor vendor = modelMapper.map(vendorRequestDTO,Vendor.class);
        vendor.setAddress(address);

        AuthDto authDto =new AuthDto();
        authDto.setDevice(vendorRequestDTO.getDevice());
        authDto.setIpAddress(vendorRequestDTO.getIpAddress());
        authDto.setPhoneNumber("67474838383");

        Session session = sessionServiceImpl.createVendorSession(authDto);
        List<Session> sessionListlist = new ArrayList<>();
        session.setVendor(vendor);
        sessionListlist.add(session);
        vendor.setSessions(sessionListlist);

        vendorRepository.save(vendor);
        sessionRepository.save(session);


        // Authenticate the vendor to generate tokens
        Authentication authentication = new UsernamePasswordAuthenticationToken(phoneNumber, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate tokens
        String accessToken = jwtTokenProvider.generateAccessToken(authentication);
        String refreshToken = jwtTokenProvider.generateRefreshToken(authentication);

        // Return VendorAuthResponse
        return new UserAuthResponse(
                null,
                vendor.getId(),
                vendor.getPhoneNumber(),
                accessToken,
                refreshToken
        );
    }

    @Override
    public boolean logoutAll(String phoneNumber) throws UserException {
        validate.phoneNumber(phoneNumber);
        Vendor vendor=vendorRepository.findByPhoneNumber(phoneNumber);
        if(vendor==null)
            return false;
        List<Session> sessionList=vendor.getSessions();

        for(Session session:sessionList){
            session.setLogged(false);
            session.setLogoutTime(LocalDateTime.now());
            session.setStatus(ApplicationConstant.DELETE);
        }

        sessionRepository.saveAll(sessionList);

        return true;

    }

    @Override
    public boolean logout(String phoneNumber, String device) throws UserException {
        validate.phoneNumber(phoneNumber);
        Vendor vendor=vendorRepository.findByPhoneNumber(phoneNumber);
        if(vendor==null)
            return false;
        Session session=sessionRepository.findByVendorAndDevice(vendor,device);
        if(session==null)
            return false;
        session.setLogged(false);
        session.setLogoutTime(LocalDateTime.now());
        session.setStatus(ApplicationConstant.DELETE);


        sessionRepository.save(session);

        return true;
    }

    @Override
    public UserAuthResponse vendorAuthLogin(AuthDto authDto) throws UserException {
        validate.phoneNumber(authDto.getPhoneNumber());

        Vendor vendor = vendorRepository.findByPhoneNumber(authDto.getPhoneNumber());
        Authentication authentication = authenticate(vendor.getPhoneNumber());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtTokenProvider.generateAccessToken(authentication);
        String refreshToken = jwtTokenProvider.generateRefreshToken(authentication);

        Session session = sessionServiceImpl.createVendorSession(authDto);
        List<Session> sessionList = new ArrayList<>();
        sessionList.add(session);
        vendor.setSessions(sessionList);
        vendorRepository.save(vendor);
        sessionRepository.save(session);

        return new UserAuthResponse(null,vendor.getId() ,vendor.getPhoneNumber(),accessToken,refreshToken);
    }
    private Authentication authenticate(String phoneNumber) {
        UserDetails userDetails = vendorService.loadUserByUsername(phoneNumber);

        if (userDetails == null) {
            throw new BadCredentialsException("Invalid Phone Number");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
