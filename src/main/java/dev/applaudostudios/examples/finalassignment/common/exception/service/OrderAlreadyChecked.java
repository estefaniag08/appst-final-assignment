package dev.applaudostudios.examples.finalassignment.common.exception.service;

public class OrderAlreadyChecked extends CheckoutServiceException{
    public OrderAlreadyChecked(String message) {
        super(message);
    }

}
