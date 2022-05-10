package dev.applaudostudios.examples.finalassignment.common.exception.product;

import dev.applaudostudios.examples.finalassignment.common.exception.RestException;
import org.springframework.http.HttpStatus;

public class ProductRelatedException extends RuntimeException implements RestException {
    public ProductRelatedException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.BAD_REQUEST;
    }
}
