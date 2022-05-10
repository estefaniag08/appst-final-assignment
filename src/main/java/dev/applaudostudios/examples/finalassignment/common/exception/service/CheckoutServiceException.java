package dev.applaudostudios.examples.finalassignment.common.exception.service;

import dev.applaudostudios.examples.finalassignment.common.exception.RestException;
import org.springframework.http.HttpStatus;

public class CheckoutServiceException extends RuntimeException implements RestException {
    public CheckoutServiceException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatusCode() {
        return null;
    }
}
