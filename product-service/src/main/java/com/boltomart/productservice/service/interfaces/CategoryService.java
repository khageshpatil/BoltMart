package com.boltomart.productservice.service.interfaces;

import java.util.List;

import com.boltomart.productservice.dto.CategoryDTO;
import com.boltomart.productservice.response.CategoryResponse;

public interface CategoryService {
	
	public List<CategoryResponse> getAllCategories() throws Exception;
	public CategoryResponse getCategoryById(Long categoryId) throws Exception;
	public CategoryResponse addCategory(CategoryDTO categoryDto) throws Exception;
	public boolean isupdateCategory(Long id,CategoryDTO categoryDto) throws Exception;
	public boolean deleteById(Long id) throws Exception;
	
}
