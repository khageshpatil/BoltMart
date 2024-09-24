package com.boltomart.productservice.response;

import lombok.Data;
import java.util.List;

@Data
public class ProductSearchResponse {

    private Long id;
    private String SKU;
    private Long vendorId;
    private String shopName;
    private String productName;
    private Integer stock;
    private String size;
    private Integer status;
    private List<ProductMediaResponse> images;
    private List<ReviewResponse> reviews;
    private double distance;
}
