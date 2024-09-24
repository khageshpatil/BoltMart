package com.boltomart.productservice.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class ProductVariationResponse {
    private Long id;
    private String variationName;
    private String variationValue;
    private BigDecimal unitPrice;
    private Integer stock;
    private Integer status;

}