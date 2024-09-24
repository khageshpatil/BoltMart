package com.boltomart.vendor_service.service;

import com.boltomart.vendor_service.constants.ApplicationConstant;
import com.boltomart.vendor_service.entity.Vendor;
import com.boltomart.vendor_service.exception.VendorException;
import com.boltomart.vendor_service.helper.VendorHelper;
import com.boltomart.vendor_service.repository.VendorRepository;
import com.boltomart.vendor_service.response.VendorResponse;
import com.boltomart.vendor_service.service.interfaces.VendorService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class VendorServiceImpl implements VendorService {

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private VendorHelper dtoConverter; 

    @Override
    public List<VendorResponse> findAllActiveVendors() throws VendorException {
        List<Vendor> vendors = vendorRepository.findByActiveSessionStatus(ApplicationConstant.ACTIVE);
        if (vendors.isEmpty()) {
            throw new VendorException("No active vendors found.");
        }
        return vendors.stream()
                .map(dtoConverter::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public VendorResponse findVendorById(Long id) throws VendorException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Vendor ID must be a positive number.");
        }
        Vendor vendor = vendorRepository.findByIdAndActiveSessionStatus(id, ApplicationConstant.ACTIVE);
        if (vendor == null) {
            throw new VendorException("Vendor not found with id: " + id);
        }
        return dtoConverter.convertToDto(vendor); 
    }

    @Override
    public List<VendorResponse> findVendorsByPincode(Integer pincode) throws VendorException {
        if (pincode == null || pincode <= 0) {
            throw new IllegalArgumentException("Pincode must be a positive number.");
        }
        List<Vendor> vendors = vendorRepository.findByAddress_PincodeAndActiveSessionStatus(pincode, ApplicationConstant.ACTIVE);
        if (vendors.isEmpty()) {
            throw new VendorException("No vendors found for pincode: " + pincode);
        }
        return vendors.stream()
                .map(dtoConverter::convertToDto) 
                .collect(Collectors.toList());
    }
}
