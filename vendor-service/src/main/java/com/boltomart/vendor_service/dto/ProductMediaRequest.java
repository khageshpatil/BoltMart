package com.boltomart.vendor_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductMediaRequest {
	private Long id;
    private String mediaUrl;
    private String mediaType; // e.g., image, video, etc.
}
