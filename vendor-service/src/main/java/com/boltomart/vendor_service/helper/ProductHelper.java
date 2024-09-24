package com.boltomart.vendor_service.helper;

import com.boltomart.vendor_service.dto.ProductMediaRequest;
import com.boltomart.vendor_service.dto.ProductUpdateRequest;
import com.boltomart.vendor_service.dto.ProductVariationRequest;
import com.boltomart.vendor_service.entity.Product;
import com.boltomart.vendor_service.entity.ProductMedia;
import com.boltomart.vendor_service.entity.ProductVariation;
import com.boltomart.vendor_service.repository.ProductMediaRepository;
import com.boltomart.vendor_service.repository.ProductVariationRepository;
import com.boltomart.vendor_service.response.ProductResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductHelper {

    @Autowired
    private ProductVariationRepository productVariationRepository;

    @Autowired
    private ProductMediaRepository productMediaRepository;

    @Autowired
    private ModelMapper modelMapper;

    
    
    //ProductServiceImpl
    public void handleVariations(Product product, ProductUpdateRequest request) {
        if (request.getVariations() != null) {
            List<ProductVariation> updatedVariations = new ArrayList<>();

            for (ProductVariationRequest variationRequest : request.getVariations()) {
                ProductVariation variation;
                if (variationRequest.getId() != null) {
                    variation = productVariationRepository.findById(variationRequest.getId())
                            .orElse(null);
                    if (variation == null) {
                        continue; // Skip this variation if doesn't exist
                    }
                    // if exist Update existing variation
                    modelMapper.map(variationRequest, variation);
                } else {
                    //new variation Created if no id provided
                    variation = modelMapper.map(variationRequest, ProductVariation.class);
                }
                variation.setProduct(product);
                updatedVariations.add(variation);
            }

            if (!product.isHasVariations() && request.isHasVariations()) {
                product.getVariations().clear();
            }
            product.getVariations().addAll(updatedVariations);
        } else if (!request.isHasVariations()) {
            if (product.isHasVariations()) {
                product.getVariations().clear();
            }
        }
    }

    public void handleImages(Product product, ProductUpdateRequest request) {
        if (request.getImages() != null) {
            List<ProductMedia> updatedImages = new ArrayList<>();

            for (ProductMediaRequest mediaRequest : request.getImages()) {
                ProductMedia media;
                if (mediaRequest.getId() != null) {
                    media = productMediaRepository.findById(mediaRequest.getId())
                            .orElse(null);
                    if (media == null) {
                        continue; // Skip this media if it doesn't exist
                    }
                    // Update existing media
                    modelMapper.map(mediaRequest, media);
                } else {
                    // Create new media if no ID provided
                    media = modelMapper.map(mediaRequest, ProductMedia.class);
                }
                media.setProduct(product);
                updatedImages.add(media);
            }

            if (product.getImages() != null && !product.getImages().isEmpty() && request.getImages().isEmpty()) {
                product.getImages().clear();
            }
            product.getImages().addAll(updatedImages);
        } else if (product.getImages() != null && !product.getImages().isEmpty()) {
            product.getImages().clear();
        }
    }

    public ProductResponse mapToProductResponse(Product product) {
        ProductResponse productResponse = modelMapper.map(product, ProductResponse.class);
        productResponse.setVendorId(product.getVendor().getId());
        return productResponse;
    }
    
   
}
