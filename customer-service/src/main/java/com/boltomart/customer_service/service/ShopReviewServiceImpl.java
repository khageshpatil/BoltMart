package com.boltomart.customer_service.service;

import com.boltomart.customer_service.constants.ApplicationConstant;
import com.boltomart.customer_service.dto.ShopReviewDto;
import com.boltomart.customer_service.entity.Customer;
import com.boltomart.customer_service.entity.ProductReview;
import com.boltomart.customer_service.entity.ShopReview;
import com.boltomart.customer_service.entity.Vendor;
import com.boltomart.customer_service.repository.CustomerRepository;

import com.boltomart.customer_service.repository.ShopReviewRepository;
import com.boltomart.customer_service.repository.VendorRepository;
import com.boltomart.customer_service.response.ShopReviewResponse;
import com.boltomart.customer_service.service.interfaces.ShopReviewService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopReviewServiceImpl implements ShopReviewService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ShopReviewRepository shopReviewRepository;

    @Autowired
    VendorRepository vendorRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public ShopReviewResponse createReview(ShopReviewDto shopReviewDto) {
        ShopReview shopReview = new ShopReview();
        Vendor shop = vendorRepository.findById(shopReviewDto.getVendorId()).orElseThrow(() -> new RuntimeException("Shop not found"));
        Customer customer = customerRepository.findById(shopReviewDto.getCustomerId()).orElseThrow(() -> new RuntimeException("Customer not found"));

        shopReview.setCustomer(customer);
        shopReview.setRating(shopReviewDto.getRating());
        shopReview.setReviewText(shopReviewDto.getReviewText());
        shopReview.setStatus(ApplicationConstant.ACTIVE);
        shopReview.setVendor(shop);
        shopReview = shopReviewRepository.save(shopReview);
        updateVendorRating(shop);
        ShopReviewResponse shopReviewResponse = new ShopReviewResponse();
        shopReviewResponse.setId(shopReview.getId());
        shopReviewResponse.setCustomerId(shopReview.getCustomer().getId());
        shopReviewResponse.setRating(shopReview.getRating());
        shopReviewResponse.setComment(shopReview.getReviewText());
        shopReviewResponse.setCreatedAt(shopReview.getCreatedAt());
        shopReviewResponse.setUpdatedAt(shopReview.getUpdatedAt());
        shopReviewResponse.setStatus(shopReview.getStatus());
        shopReviewResponse.setVendorId(shopReview.getVendor().getId());
        return shopReviewResponse;
    }

    @Override
    public ShopReviewResponse getReview(Long id) {
        ShopReview shopReview=shopReviewRepository.findByIdAndStatus(id,ApplicationConstant.ACTIVE);
        if(shopReview ==null)
            throw  new RuntimeException("Review not found");
        ShopReviewResponse shopReviewResponse = new ShopReviewResponse();
        shopReviewResponse.setId(shopReview.getId());
        shopReviewResponse.setCustomerId(shopReview.getCustomer().getId());
        shopReviewResponse.setRating(shopReview.getRating());
        shopReviewResponse.setComment(shopReview.getReviewText());
        shopReviewResponse.setCreatedAt(shopReview.getCreatedAt());
        shopReviewResponse.setUpdatedAt(shopReview.getUpdatedAt());
        shopReviewResponse.setStatus(shopReview.getStatus());
        shopReviewResponse.setVendorId(shopReview.getVendor().getId());
        return shopReviewResponse;
    }

    @Override
    public List<ShopReviewResponse> getReviewsByCustomerId(Long custId) {
        return shopReviewRepository.findByCustomer_Id(custId);
    }

    @Override
    public List<ShopReviewResponse> getReviewsByVendorId(Long vendorId) {
        return shopReviewRepository.findByProduct_Id(vendorId);

    }

    @Override
    public boolean deleteReview(Long id) {
        ShopReview shopReview = shopReviewRepository.findByIdAndStatus(id,ApplicationConstant.ACTIVE);
        //if address not found
        if(shopReview ==null)
            return false;

        shopReview.setStatus(ApplicationConstant.DELETE);
        shopReviewRepository.save(shopReview);
        return true;
    }
    private void updateVendorRating(Vendor vendor) {
        Float averageRating=shopReviewRepository.findAverageRatingByVendorId(vendor.getId());
        vendor.setRating( averageRating);
        vendorRepository.save(vendor);
    }
}
