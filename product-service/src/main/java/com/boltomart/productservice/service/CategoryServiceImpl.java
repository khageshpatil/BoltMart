package com.boltomart.productservice.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.boltomart.productservice.constants.ApplicationConstant;
import com.boltomart.productservice.dto.CategoryDTO;
import com.boltomart.productservice.dto.SubCategoryDTO;
import com.boltomart.productservice.entity.Category;
import com.boltomart.productservice.exception.ProductServiceException;
import com.boltomart.productservice.repository.CategoryRepository;
import com.boltomart.productservice.response.CategoryResponse;
import com.boltomart.productservice.service.interfaces.CategoryService;
import com.boltomart.productservice.validations.Validations;

@Async
@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<CategoryResponse> getAllCategories() throws Exception {
		System.out.println("Fetching all categories...");
		try {
			List<Category> categories = categoryRepository.getAllCategories();
			System.out.println("Retrieved " + categories.size() + " categories.");

			List<CategoryResponse> categoryResponse = categories.stream().map(category -> {
				CategoryResponse response = modelMapper.map(category, CategoryResponse.class);
				// Filter out subcategories with status 40
				List<SubCategoryDTO> filteredSubCategories = response.getSubCategories().stream()
						.filter(subCat -> subCat.getStatus() != 40).collect(Collectors.toList());
				response.setSubCategories(filteredSubCategories);
				return response;
			}).collect(Collectors.toList());
			return categoryResponse;
		} catch (Exception e) {
			System.out.println("Error retrieving products: " + e.getMessage());
			throw new Exception("Error retrieving products: " + e.getMessage(), e);
		}
	}

	@Override
	public CategoryResponse getCategoryById(Long categoryId) throws Exception {
		System.out.println("Fetching product with ID: " + categoryId);
		Optional<Category> categoryOptional = categoryRepository.findByCategoryId(categoryId);
		Category category = categoryOptional.orElseThrow(() -> {
			System.out.println("Category with ID " + categoryOptional + " not found.");
			return new ProductServiceException("Category with ID " + categoryOptional + " not found.");
		});
		CategoryResponse response = modelMapper.map(category, CategoryResponse.class);
		// Filter out subcategories with status 40
		List<SubCategoryDTO> filteredSubCategories = response.getSubCategories().stream()
				.filter(subCat -> subCat.getStatus() != 40).collect(Collectors.toList());

		response.setSubCategories(filteredSubCategories);

		return response;
	}

	@Override
	@Transactional
	public CategoryResponse addCategory(CategoryDTO categoryDto) throws Exception {
		try {
			System.out.println("Adding category: " + categoryDto.getCategoryName());
			Validations.validateCategory(categoryDto);
			categoryDto.setStatus(ApplicationConstant.ACTIVE);
			Category parentCategory = null;
			if (categoryDto.getParentCategory() != null) {
				parentCategory = categoryRepository.findById(categoryDto.getParentCategory())
						.orElseThrow(() -> new Exception("Parent category not found"));
			}
			Category category = new Category(categoryDto.getId(), categoryDto.getCategoryName(),
					categoryDto.getStatus(), parentCategory, null, null, categoryDto.getCreatedAt(),
					categoryDto.getUpdatedAt());
			Category savedCategory = categoryRepository.save(category);
			return modelMapper.map(savedCategory, CategoryResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProductServiceException("Error creating product: " + e.getMessage());
		}
	}

	@Override
	public boolean isupdateCategory(Long id, CategoryDTO categoryDto) throws Exception {
		boolean res = false;
	    try {
	        Category existingCategory = categoryRepository.findById(id).orElse(null);
	        if (existingCategory != null) {
	            if (categoryDto.getCategoryName() != null) {
	                existingCategory.setCategoryName(categoryDto.getCategoryName());
	            }
	            if (categoryDto.getStatus() != null) {
	                existingCategory.setStatus(categoryDto.getStatus());
	            }
	            existingCategory.setUpdatedAt(LocalDateTime.now());
	            Category updatedCategory = categoryRepository.save(existingCategory);
	            if (updatedCategory != null) {
	                res = true;
	            }
	        }
		} catch (Exception e) {
			throw new ProductServiceException("Error updating category: " + e.getMessage());
		}
		return res;
	}

	@Override
	public boolean deleteById(Long id) throws Exception {
		boolean res = false;
		if (categoryRepository.existsById(id)) {
			Category category = categoryRepository.findById(id).orElse(null);
			if (category != null) {
				category.setStatus(ApplicationConstant.DELETE);
				categoryRepository.save(category);
				res = true;
			}
		}
		return res;
	}

}
