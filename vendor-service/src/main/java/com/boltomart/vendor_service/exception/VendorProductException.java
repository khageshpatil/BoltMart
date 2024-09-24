package com.boltomart.vendor_service.exception;

public class VendorProductException extends RuntimeException {
    public VendorProductException(String message) {
        super(message);
    }

    public VendorProductException(String message, Throwable cause) {
        super(message, cause);
    }
}
