package com.boltomart.productservice.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boltomart.productservice.dto.CategoryDTO;
import com.boltomart.productservice.entity.Category;
import com.boltomart.productservice.entity.Product;
import com.boltomart.productservice.exception.ProductServiceException;
import com.boltomart.productservice.response.CategoryResponse;
import com.boltomart.productservice.response.ProductResponse;
import com.boltomart.productservice.service.interfaces.CategoryService;
import com.boltomart.productservice.service.interfaces.ProductService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@GetMapping
	public ResponseEntity<List<CategoryResponse>> getAllCategories() throws Exception {
		ResponseEntity<List<CategoryResponse>> response;
		try {
			List<CategoryResponse> categories = categoryService.getAllCategories();
			response = ResponseEntity.ok(categories);
		} catch (Exception e) {
			throw new ProductServiceException("Error retrieving categories: " + e.getMessage());
		}
		return response;
	}

	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Long categoryId) throws Exception {
		ResponseEntity<CategoryResponse> response = null;
		try {
			if (categoryId != 0 || categoryId != null) {
				CategoryResponse categoryResponse = categoryService.getCategoryById(categoryId);
				if (categoryResponse != null) {
					response = ResponseEntity.ok(categoryResponse);
				} else {
					response = ResponseEntity.notFound().build();
				}
			} else {
				throw new ProductServiceException("Category Id is not found");
			}
		} catch (Exception e) {
			throw new ProductServiceException("Error retrieving product: " + e.getMessage());
		}
		return response;
	}

	@PostMapping
	public ResponseEntity<String> addCategory(@RequestBody CategoryDTO categoryDto) throws Exception {
		ResponseEntity<String> response = null;
		try {

			CategoryResponse categoryResponse = categoryService.addCategory(categoryDto);
			if (categoryResponse != null)
				response = ResponseEntity.status(HttpStatus.CREATED).body("Category added successfully.");
			else
				response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body("Category could not be created");

		} catch (Exception e) {
			throw new ProductServiceException("Error creating Category: " + e.getMessage());
		}
		return response;
	}

	@PutMapping("/{id}")
	public ResponseEntity<String> updateCategory(@PathVariable Long id, @RequestBody CategoryDTO categoryDto)
			throws Exception {
		ResponseEntity<String> response;
		try {

			boolean isupdatedCategory = categoryService.isupdateCategory(id, categoryDto);
			if (isupdatedCategory)
				response = ResponseEntity.status(HttpStatus.CREATED).body("Category updated successfully.");
			else
				response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body("Category not updated successfully.");

		} catch (Exception e) {
			throw new ProductServiceException("Error updating category: " + e.getMessage());
		}
		return response;
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteCategory(@PathVariable Long id) throws Exception {
		ResponseEntity<String> response;
		try {
			if (id != null) {
				boolean isDeleted = categoryService.deleteById(id);
				if (isDeleted) {
					response = new ResponseEntity<>("Category deleted successfully.", HttpStatus.OK);
				} else {
					response = new ResponseEntity<>("Category not found.", HttpStatus.NOT_FOUND);
				}
			} else {
				throw new ProductServiceException("Error deleting category: " + id + "Not Found");
			}

		} catch (Exception e) {
			throw new ProductServiceException("Error deleting category: " + e.getMessage());
		}
		return response;
	}
}
