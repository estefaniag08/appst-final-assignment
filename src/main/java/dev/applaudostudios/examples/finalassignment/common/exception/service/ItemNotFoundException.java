package dev.applaudostudios.examples.finalassignment.common.exception.service;

import org.springframework.http.HttpStatus;

public class ItemNotFoundException extends CheckoutServiceException{
    public ItemNotFoundException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.NOT_FOUND;
    }
}
