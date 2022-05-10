package dev.applaudostudios.examples.finalassignment.common.exception.user;

import org.springframework.http.HttpStatus;

public class AddressNotExistException extends UserRelatedException {
    public AddressNotExistException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.NOT_FOUND;
    }
}
