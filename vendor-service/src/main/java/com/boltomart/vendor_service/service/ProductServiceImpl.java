package com.boltomart.vendor_service.service;

import com.boltomart.vendor_service.entity.Product;
import com.boltomart.vendor_service.exception.VendorProductException;
import com.boltomart.vendor_service.repository.ProductMediaRepository;
import com.boltomart.vendor_service.repository.ProductRepository;
import com.boltomart.vendor_service.repository.ProductVariationRepository;
import com.boltomart.vendor_service.response.ProductResponse;
import com.boltomart.vendor_service.service.interfaces.ProductService;
import com.boltomart.vendor_service.constants.ApplicationConstant;
import com.boltomart.vendor_service.dto.ProductUpdateRequest;
import com.boltomart.vendor_service.validation.ProductValidator;
import com.boltomart.vendor_service.helper.ProductHelper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProductValidator productValidator;

    @Autowired
    private ProductHelper productHelper;

    @Transactional
    public ProductResponse updateProduct(Long vendorId, Long productId, ProductUpdateRequest productUpdateRequest) throws VendorProductException {
        try {
            // Validating inputs
            productValidator.validateProductUpdateRequest(productUpdateRequest);

            // Fetching the product from the repository
            Product product = productRepository.findByIdAndVendorId(productId, vendorId)
                    .orElseThrow(() -> new VendorProductException("Product with ID " + productId + " for vendor ID " + vendorId + " not found."));

            // Updating basic product fields from the DTO as model mapper causes ambiguity, Due to vendor Id and product Id circular referance
            product.setSKU(productUpdateRequest.getSKU());
            product.setProductName(productUpdateRequest.getProductName());
            product.setProductDescription(productUpdateRequest.getProductDescription());
            product.setHasVariations(productUpdateRequest.isHasVariations());
            product.setUnitPrice(productUpdateRequest.getUnitPrice());
            product.setStock(productUpdateRequest.getStock());
            product.setSize(productUpdateRequest.getSize());
            product.setNotes(productUpdateRequest.getNotes());
            product.setStatus(productUpdateRequest.getStatus());
            product.setUpdatedAt(LocalDateTime.now());


            // variations handled
            productHelper.handleVariations(product, productUpdateRequest);

            // images handled
            productHelper.handleImages(product, productUpdateRequest);

            //updated product saved
            Product savedProduct = productRepository.save(product);

            
            return modelMapper.map(savedProduct, ProductResponse.class);
        } catch (VendorProductException e) {
            throw e;
        } catch (Exception e) {
            throw new VendorProductException("Error updating product: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public void deleteProduct(Long vendorId, Long productId) throws Exception {
        Optional<Product> optionalProduct = productRepository.findByIdAndVendorId(productId, vendorId);
        if (!optionalProduct.isPresent()) {
            throw new VendorProductException("Product with ID " + productId + " for vendor ID " + vendorId + " not found.");
        }

        Product product = optionalProduct.get();
        product.setStatus(ApplicationConstant.DELETE);
        productRepository.save(product);
    }

    @Override
    public List<ProductResponse> searchProducts(Long vendorId, String productName, String categoryName, BigDecimal minPrice, BigDecimal maxPrice) {
        productValidator.validateVendorId(vendorId);
        productValidator.validatePrice(minPrice, maxPrice);
        List<Product> products = productRepository.searchProducts(vendorId, productName, categoryName, minPrice, maxPrice);

        return products.stream()
                .map(productHelper::mapToProductResponse)
                .collect(Collectors.toList());
    }
}
