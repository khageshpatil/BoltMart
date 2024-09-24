package com.boltomart.auth_service.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Getter
@Setter
@AllArgsConstructor
public class UserAuthResponse {


    private Long customerId=null;
    private Long vendorId=null;
    private String phoneNumber;
    private String accessToken;
    private String refreshToken;

}
