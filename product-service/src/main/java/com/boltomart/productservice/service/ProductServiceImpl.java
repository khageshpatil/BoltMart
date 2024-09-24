package com.boltomart.productservice.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.boltomart.productservice.constants.ApplicationConstant;
import com.boltomart.productservice.entity.Category;
import com.boltomart.productservice.entity.Product;
import com.boltomart.productservice.entity.ProductMedia;
import com.boltomart.productservice.entity.ProductVariation;
import com.boltomart.productservice.exception.ProductServiceException;
import com.boltomart.productservice.repository.CategoryRepository;
import com.boltomart.productservice.repository.ProductRepository;
import com.boltomart.productservice.response.CategoryResponse;
import com.boltomart.productservice.response.ProductResponse;
import com.boltomart.productservice.response.ProductSearchResponse;
import com.boltomart.productservice.service.interfaces.ProductService;
import com.boltomart.productservice.util.GeoUtil;
import com.boltomart.productservice.validations.Validations;

@Async
@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<ProductResponse> getAllProducts() throws Exception {
		System.out.println("Fetching all products...");
		try {
			List<Product> products = productRepository.findAllProducts();
			System.out.println("Retrieved " + products.size() + " products.");

			List<ProductResponse> productResponse = products.stream().map(product -> {
				ProductResponse response = modelMapper.map(product, ProductResponse.class);
				response.setVendorId(product.getVendor().getId());
				return response;
			}).collect(Collectors.toList());
			return productResponse;

		} catch (Exception e) {
			System.out.println("Error retrieving products: " + e.getMessage());
			throw new Exception("Error retrieving products: " + e.getMessage(), e);
		}
	}

	@Override
	public ProductResponse getProductById(Long id) throws ProductServiceException {
		System.out.println("Fetching product with ID: " + id);
		Optional<Product> productOptional = productRepository.findById(id);
		Product product = productOptional.orElseThrow(() -> {
			System.out.println("Product with ID " + id + " not found.");
			return new ProductServiceException("Product with ID " + id + " not found.");
		});
		return modelMapper.map(product, ProductResponse.class);
	}

	@Override
	public List<ProductResponse> getVendorProducts(Integer vendorId) throws Exception {
		System.out.println("Fetching products for vendor ID: " + vendorId);
		try {
			List<Product> vendorProducts = productRepository.findByVendorId(vendorId);
			System.out.println("Retrieved " + vendorProducts.size() + " products for vendor ID: " + vendorId);
			return vendorProducts.stream().map(product -> modelMapper.map(product, ProductResponse.class))
					.collect(Collectors.toList());
		} catch (Exception e) {
			System.out.println("Error retrieving vendor products: " + e.getMessage());
			throw new Exception("Error retrieving vendor products: " + e.getMessage(), e);
		}
	}

	@Override
	@Transactional
	public ProductResponse addProduct(Product product) throws Exception {
		try {
			System.out.println("Adding product: " + product.getProductName());

			Validations.validateProduct(product);
			product.setStatus(ApplicationConstant.ACTIVE);

			Product savedProduct = productRepository.save(product);
			System.out.println("Product saved with ID: " + savedProduct.getId());
			if (product.isHasVariations() && product.getVariations() != null) {
				List<ProductVariation> variations = new ArrayList<>();
				for (ProductVariation variation : product.getVariations()) {
					variation.setProduct(savedProduct); // Set the product for each variation
					variations.add(variation); // Add to the list of variations
				}

				savedProduct.setVariations(variations);
			}

			// Map images and save them
			if (product.getImages() != null) {
				// Create a new list to collect images
				List<ProductMedia> images = new ArrayList<>();

				// Loop through each image and set the product
				for (ProductMedia image : product.getImages()) {
					image.setProduct(savedProduct); // Set the product for each image
					images.add(image); // Add to the list of images
				}

				// Set images to the saved product
				savedProduct.setImages(images);
			}

			// Step 5: Update the product with variations and images
			savedProduct = productRepository.save(savedProduct);
			System.out.println("Product updated with variations and images.");

			// Step 6: Return mapped response of the saved product
			return modelMapper.map(savedProduct, ProductResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProductServiceException("Error creating product: " + e.getMessage());
		}
	}
	
	@Override
	public List<ProductSearchResponse> getProductsByCategory(Long categoryId, Double latitude, Double longitude, Double radius) throws Exception {
	    System.out.println("getProductsByCategory called with categoryId: " + categoryId + ", latitude: " + latitude + ", longitude: " + longitude + ", radius: " + radius);

	    if (!categoryRepository.existsById(categoryId)) {
	        throw new Exception("Category not found, please enter a valid category id");
	    }

	    if (latitude == null || longitude == null || radius == null) {
	        throw new Exception("Latitude, longitude, and radius must be provided");
	    }

	    Double[] boundingBox = GeoUtil.calculateBoundingBox(latitude, longitude, radius);
	    Double minLat = boundingBox[0];
	    Double maxLat = boundingBox[1];
	    Double minLon = boundingBox[2];
	    Double maxLon = boundingBox[3];

	    System.out.println("BoundingBox minLat=" + minLat + ", maxLat=" + maxLat + ", minLon=" + minLon + ", maxLon=" + maxLon);
	   
	    List<Object[]> results = productRepository.findProductsByCategoryAndLocation(longitude, latitude, minLat, maxLat, minLon, maxLon, radius, categoryId);
	    
	    System.out.println("Query executed, number of results: " + results.size());

	    return results.stream().map(result -> {
	        Long id = ((Number) result[0]).longValue();
	        String SKU = (String) result[1];
	        Long vendorId = ((Number) result[2]).longValue();
	        String shopNameResult = (String) result[3];
	        String productNameResult = (String) result[4];
	        Integer stock = ((Number) result[5]).intValue();
	        String size = (String) result[6];
	        Integer status = ((Number) result[7]).intValue();
	        double distance = ((Number) result[8]).doubleValue();

	        ProductSearchResponse response = new ProductSearchResponse();
	        response.setId(id);
	        response.setSKU(SKU);
	        response.setVendorId(vendorId);
	        response.setShopName(shopNameResult);
	        response.setProductName(productNameResult);
	        response.setStock(stock);
	        response.setSize(size);
	        response.setStatus(status);
	        response.setDistance(distance);

	        return response;
	    }).collect(Collectors.toList());
	}



	
	 @Override
	 public List<ProductSearchResponse> searchProducts(Double latitude, Double longitude, Double radius,
	         String productName, String shopName, String categoryName,
	         BigDecimal minPrice, BigDecimal maxPrice) {

	     Double[] boundingBox = GeoUtil.calculateBoundingBox(latitude, longitude, radius);
	     Double minLat = boundingBox[0];
	     Double maxLat = boundingBox[1];
	     Double minLon = boundingBox[2];
	     Double maxLon = boundingBox[3];

	     List<Object[]> results = productRepository.searchProductsWithDistance(longitude, latitude, minLat, maxLat, minLon, maxLon, radius, productName, shopName, categoryName, minPrice, maxPrice);

	     return results.stream().map(result -> {
	         Long id = ((Number) result[0]).longValue();
	         String SKU = (String) result[1];
	         Long vendorId = ((Number) result[2]).longValue();
	         String shopNameResult = (String) result[3];
	         String productNameResult = (String) result[4];
	         Integer stock = ((Number) result[5]).intValue();
	         String size = (String) result[6];
	         Integer status = ((Number) result[7]).intValue();
	         double distance = ((Number) result[8]).doubleValue();

	         ProductSearchResponse response = new ProductSearchResponse();
	         response.setId(id);
	         response.setSKU(SKU);
	         response.setVendorId(vendorId);
	         response.setShopName(shopNameResult);
	         response.setProductName(productNameResult);
	         response.setStock(stock);
	         response.setSize(size);
	         response.setStatus(status);
	         response.setDistance(distance);

	         return response;
	     }).collect(Collectors.toList());
	 }


}