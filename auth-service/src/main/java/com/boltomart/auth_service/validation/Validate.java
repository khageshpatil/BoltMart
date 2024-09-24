package com.boltomart.auth_service.validation;

import com.boltomart.auth_service.exception.UserException;
import org.springframework.context.annotation.Bean;

public class Validate {

    public void phoneNumber(String phoneNumber) throws UserException {
        if (phoneNumber == null || phoneNumber.length() < 10 || phoneNumber.length() > 13) {
            throw new UserException("Invalid phone number. Phone number must be between 10 and 13 characters.");
        }
    }
}
