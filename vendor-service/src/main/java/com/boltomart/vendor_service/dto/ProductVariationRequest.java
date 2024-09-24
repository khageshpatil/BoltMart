package com.boltomart.vendor_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductVariationRequest {
	private Long id;
    private String variationName;
    private String variationValue;
}
