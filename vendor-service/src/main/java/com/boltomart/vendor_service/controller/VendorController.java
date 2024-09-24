package com.boltomart.vendor_service.controller;

import com.boltomart.vendor_service.exception.VendorException;
import com.boltomart.vendor_service.response.VendorResponse;
import com.boltomart.vendor_service.service.interfaces.VendorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vendors")
public class VendorController {

    @Autowired
    private VendorService vendorService;

    @GetMapping("")
    public ResponseEntity<List<VendorResponse>> getAllVendors() throws VendorException {
        List<VendorResponse> vendors = vendorService.findAllActiveVendors();
        return ResponseEntity.ok(vendors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VendorResponse> getVendorById(@PathVariable Long id) throws VendorException {
        VendorResponse vendor = vendorService.findVendorById(id);
        return ResponseEntity.ok(vendor);
    }

    @GetMapping("/search/by-pincode")
    public ResponseEntity<List<VendorResponse>> getVendorsByPincode(@RequestParam Integer pincode) throws VendorException {
        List<VendorResponse> vendors = vendorService.findVendorsByPincode(pincode);
        return ResponseEntity.ok(vendors);
    }
}
