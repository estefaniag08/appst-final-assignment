package dev.applaudostudios.examples.finalassignment.common.exception.order;

import org.springframework.http.HttpStatus;

public class OrderNotFoundException extends OrderRelatedException {
    public OrderNotFoundException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.NOT_FOUND;
    }
}
