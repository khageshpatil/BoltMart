package com.boltomart.customer_service.service;

import com.boltomart.customer_service.constants.ApplicationConstant;
import com.boltomart.customer_service.dto.ProductReviewDto;
import com.boltomart.customer_service.entity.Customer;
import com.boltomart.customer_service.entity.Product;
import com.boltomart.customer_service.entity.ProductReview;
import com.boltomart.customer_service.entity.Vendor;
import com.boltomart.customer_service.repository.CustomerRepository;
import com.boltomart.customer_service.repository.ProductRepository;
import com.boltomart.customer_service.repository.ProductReviewRepository;
import com.boltomart.customer_service.response.ProductReviewResponse;
import com.boltomart.customer_service.service.interfaces.ProductReviewService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductReviewServiceImpl implements ProductReviewService {


    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ProductReviewRepository productReviewRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public ProductReviewResponse createReview(ProductReviewDto productReviewDto) {
        ProductReview productReview = new ProductReview();
        Product product = productRepository.findById(productReviewDto.getProductId()).orElseThrow(() -> new RuntimeException("Product not found"));
        Customer customer = customerRepository.findById(productReviewDto.getCustomerId()).orElseThrow(() -> new RuntimeException("Customer not found"));

        productReview.setCustomer(customer);
        productReview.setRating(productReviewDto.getRating());
        productReview.setReviewText(productReviewDto.getReviewText());
        productReview.setStatus(ApplicationConstant.ACTIVE);
        productReview.setProduct(product);
        productReview = productReviewRepository.save(productReview);
        updateProductRating(product);
        ProductReviewResponse productReviewResponse = new ProductReviewResponse();
        productReviewResponse.setId(productReview.getId());
        productReviewResponse.setCustomerId(productReview.getCustomer().getId());
        productReviewResponse.setRating(productReview.getRating());
        productReviewResponse.setComment(productReview.getReviewText());
        productReviewResponse.setCreatedAt(productReview.getCreatedAt());
        productReviewResponse.setUpdatedAt(productReview.getUpdatedAt());
        productReviewResponse.setStatus(productReview.getStatus());
        productReviewResponse.setProductId(productReview.getProduct().getId());
        return productReviewResponse;
    }

    @Override
    public ProductReviewResponse getReview(Long id) {
            ProductReview productReview = productReviewRepository.findByIdAndStatus(id,ApplicationConstant.ACTIVE);
            if(productReview ==null)
                throw  new RuntimeException("Review not found");
            ProductReviewResponse productReviewResponse = new ProductReviewResponse();
            productReviewResponse.setId(productReview.getId());
            productReviewResponse.setCustomerId(productReview.getCustomer().getId());
            productReviewResponse.setRating(productReview.getRating());
            productReviewResponse.setComment(productReview.getReviewText());
            productReviewResponse.setCreatedAt(productReview.getCreatedAt());
            productReviewResponse.setUpdatedAt(productReview.getUpdatedAt());
            productReviewResponse.setStatus(productReview.getStatus());
            productReviewResponse.setProductId(productReview.getProduct().getId());
            return productReviewResponse;
        }

    @Override
    public List<ProductReviewResponse> getReviewsByCustomerId(Long custId) {
        return productReviewRepository.findByCustomer_Id(custId);
    }

    @Override
    public List<ProductReviewResponse> getReviewsByProductId(Long productId) {
        return productReviewRepository.findByProduct_Id(productId);
    }

    @Override
    public boolean deleteReview(Long id) {
        ProductReview productReview = productReviewRepository.findByIdAndStatus(id,ApplicationConstant.ACTIVE);
        //if address not found
        if(productReview ==null)
            return false;

        productReview.setStatus(ApplicationConstant.DELETE);
        productReviewRepository.save(productReview);
        return true;
    }


    private void updateProductRating(Product product) {
        Float averageRating=productReviewRepository.findAverageRatingByProductId(product.getId());
        product.setRating(averageRating);
        productRepository.save(product);
    }
}




