package dev.applaudostudios.examples.finalassignment.common.exception.user;

import org.springframework.http.HttpStatus;

public class PaymentNotExistException extends UserRelatedException {
    public PaymentNotExistException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.NOT_FOUND;
    }
}
