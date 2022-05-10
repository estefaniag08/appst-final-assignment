package dev.applaudostudios.examples.finalassignment.common.exception.service;

public class ItemAlreadyExistsException extends CheckoutServiceException{

    public ItemAlreadyExistsException(String message) {
        super(message);
    }
}
