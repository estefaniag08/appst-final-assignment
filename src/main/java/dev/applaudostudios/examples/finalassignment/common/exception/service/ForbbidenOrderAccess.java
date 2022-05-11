package dev.applaudostudios.examples.finalassignment.common.exception.service;

import org.springframework.http.HttpStatus;

public class ForbbidenOrderAccess extends CheckoutServiceException{
    public ForbbidenOrderAccess(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.FORBIDDEN;
    }
}
