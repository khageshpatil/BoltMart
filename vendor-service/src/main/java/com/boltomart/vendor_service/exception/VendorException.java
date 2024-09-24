package com.boltomart.vendor_service.exception;

public class VendorException extends RuntimeException {
    public VendorException(String message) {
        super(message);
    }

    public VendorException(String message, Throwable cause) {
        super(message, cause);
    }
}
