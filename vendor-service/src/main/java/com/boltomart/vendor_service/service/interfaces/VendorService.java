package com.boltomart.vendor_service.service.interfaces;

import com.boltomart.vendor_service.exception.VendorException;
import com.boltomart.vendor_service.response.VendorResponse;

import java.util.List;

public interface VendorService {
    List<VendorResponse> findAllActiveVendors() throws VendorException;
    VendorResponse findVendorById(Long id) throws VendorException;
    List<VendorResponse> findVendorsByPincode(Integer pincode) throws VendorException;
}
