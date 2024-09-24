package com.boltomart.productservice.response;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import com.boltomart.productservice.dto.SubCategoryDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryResponse {
	private Long id;
	private String categoryName;
   // private String categoryDescription;
    private Integer status;
    private List<SubCategoryDTO> subCategories;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
//    private List<ProductResponse> products;
	
}
	