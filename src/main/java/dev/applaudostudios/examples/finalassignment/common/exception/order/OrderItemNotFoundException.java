package dev.applaudostudios.examples.finalassignment.common.exception.order;

import org.springframework.http.HttpStatus;

public class OrderItemNotFoundException extends OrderRelatedException{
    public OrderItemNotFoundException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.NOT_FOUND;
    }
}
