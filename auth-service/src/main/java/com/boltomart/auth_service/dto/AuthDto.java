package com.boltomart.auth_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@Data

public class AuthDto {
	@JsonProperty("phoneNumber")
	private String phoneNumber;


	@JsonProperty("device")
	private String device;

	@JsonProperty("ipAddress")
	private String ipAddress;

}
