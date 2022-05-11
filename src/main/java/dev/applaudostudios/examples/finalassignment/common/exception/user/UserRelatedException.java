package dev.applaudostudios.examples.finalassignment.common.exception.user;

import dev.applaudostudios.examples.finalassignment.common.exception.RestException;
import org.springframework.http.HttpStatus;

import java.util.List;

public class UserRelatedException extends RuntimeException implements RestException {

    public UserRelatedException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    @Override
    public List<?> getErrors() {
        return null;
    }
}
