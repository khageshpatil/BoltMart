package com.boltomart.auth_service.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.boltomart.auth_service.constants.ApplicationConstant;
import com.boltomart.auth_service.entity.Session;
import com.boltomart.auth_service.entity.Vendor;
import com.boltomart.auth_service.repository.SessionRepository;
import com.boltomart.auth_service.validation.Validate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.boltomart.auth_service.config.JwtTokenProvider;
import com.boltomart.auth_service.dto.AuthDto;
import com.boltomart.auth_service.entity.Customer;
import com.boltomart.auth_service.exception.UserException;
import com.boltomart.auth_service.repository.CustomerRepository;
import com.boltomart.auth_service.response.UserAuthResponse;
import com.boltomart.auth_service.service.interfaces.CustomerAuthService;

@Service
public class CustomerAuthServiceImpl implements CustomerAuthService {

    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private SessionServiceImpl sessionServiceImpl;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private Validate validate;



    @Override
    public UserAuthResponse authSignup(AuthDto authDto) throws UserException {
        String phoneNumber = authDto.getPhoneNumber();
        validate.phoneNumber(phoneNumber);
        Customer isCustomer = customerRepository.findByPhoneNumber(phoneNumber);

        if (isCustomer != null) {
            throw new UserException("Phone Number Is Already Used With Another Account");

        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(phoneNumber, null, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtTokenProvider.generateAccessToken(authentication);
        String refreshToken = jwtTokenProvider.generateRefreshToken(authentication);

        Customer customer = new Customer();
        customer.setPhoneNumber(phoneNumber);

        Session session = sessionServiceImpl.createCustomerSession(authDto);
        List<Session> sessionListlist = new ArrayList<>();
        session.setCustomer(customer);
        sessionListlist.add(session);
        customer.setSessions(sessionListlist);

        customerRepository.save(customer);
        sessionRepository.save(session);

        return new UserAuthResponse(customer.getId(),null, customer.getPhoneNumber(),accessToken,refreshToken);

    }



    public UserAuthResponse customerAuthLogin(AuthDto authDto) throws UserException {
        validate.phoneNumber(authDto.getPhoneNumber());

        Customer customer = customerRepository.findByPhoneNumber(authDto.getPhoneNumber());

        Authentication authentication = authenticateCustomer(authDto.getPhoneNumber());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtTokenProvider.generateAccessToken(authentication);
        String refreshToken = jwtTokenProvider.generateRefreshToken(authentication);

        Session session = sessionServiceImpl.createCustomerSession(authDto);
        List<Session> sessionListlist = new ArrayList<>();
        sessionListlist.add(session);
        customer.setSessions(sessionListlist);
        customerRepository.save(customer);
        sessionRepository.save(session);

        return new UserAuthResponse(customer.getId(), null,customer.getPhoneNumber(),accessToken,refreshToken);
    }


    private Authentication authenticateCustomer(String phoneNumber) {
        UserDetails userDetails = customerService.loadUserByUsername(phoneNumber);
        if (userDetails == null) {
            throw new BadCredentialsException("Invalid Phone Number");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    @Override
    public boolean logoutAll(String phoneNumber) throws UserException {

        validate.phoneNumber(phoneNumber);
        Customer customer=customerRepository.findByPhoneNumber(phoneNumber);
        if(customer==null)
            return false;
        List<Session> sessionList=customer.getSessions();


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
        Customer customer=customerRepository.findByPhoneNumber(phoneNumber);
        if(customer==null)
            return false;
        Session session=sessionRepository.findByCustomerAndDevice(customer,device);


        if(session==null)
            return false;
            session.setLogged(false);
            session.setLogoutTime(LocalDateTime.now());
            session.setStatus(ApplicationConstant.DELETE);


        sessionRepository.save(session);

        return true;
    }
}
