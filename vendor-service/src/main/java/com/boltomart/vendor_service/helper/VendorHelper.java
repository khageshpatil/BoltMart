package com.boltomart.vendor_service.helper;

import com.boltomart.vendor_service.entity.Address;
import com.boltomart.vendor_service.entity.Vendor;
import com.boltomart.vendor_service.response.AddressResponse;
import com.boltomart.vendor_service.response.VendorResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VendorHelper {

    @Autowired
    private ModelMapper modelMapper;

    public VendorResponse convertToDto(Vendor vendor) {
        VendorResponse vendorResponse = modelMapper.map(vendor, VendorResponse.class);
        vendorResponse.setAddress(convertToAddressDto(vendor.getAddress()));
        return vendorResponse;
    }

    public AddressResponse convertToAddressDto(Address address) {
        return modelMapper.map(address, AddressResponse.class);
    }
}
