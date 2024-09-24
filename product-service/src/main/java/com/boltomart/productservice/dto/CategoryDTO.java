package com.boltomart.productservice.dto;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import com.boltomart.productservice.entity.Category;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class CategoryDTO {
	private Long id;
	private String categoryName;
    private Integer status;
    private Long parentCategory; 
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
//    private List<ProductResponse> products;
	
}
