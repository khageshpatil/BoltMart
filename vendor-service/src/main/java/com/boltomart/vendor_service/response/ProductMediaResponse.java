package com.boltomart.vendor_service.response;

import java.time.LocalDateTime;

import com.boltomart.vendor_service.enums.MediaType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductMediaResponse {
    private Long id;
    private String imgUrl;
    private String imgHash;
    private MediaType imgType;
    private Integer status;

}