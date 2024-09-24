package com.boltomart.vendor_service.response;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerResponse {
	private Long id;
	private String firstName;
	private String lastName;
	private String imgUrl;
	private String phoneNumber;
	private String email;
	// private List<AddressResponse> addresses;
	// private List<SessionResponse> sessions;
	// private List<OrderResponse> orders;
	// private List<ReviewResponse> reviews;
	// private List<CartResponse> carts;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
