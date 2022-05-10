package dev.applaudostudios.examples.finalassignment.common.exception.order;

import dev.applaudostudios.examples.finalassignment.common.exception.RestException;
import org.springframework.http.HttpStatus;

public class OrderRelatedException extends RuntimeException implements RestException {
    public OrderRelatedException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
