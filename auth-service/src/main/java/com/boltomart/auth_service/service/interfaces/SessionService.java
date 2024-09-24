package com.boltomart.auth_service.service.interfaces;

import com.boltomart.auth_service.dto.AuthDto;
import com.boltomart.auth_service.entity.Session;

public interface SessionService {
    Session createCustomerSession(AuthDto authDto);
    Session createVendorSession(AuthDto authDto);
}
