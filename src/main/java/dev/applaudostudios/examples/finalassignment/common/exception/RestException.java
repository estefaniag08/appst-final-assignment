package dev.applaudostudios.examples.finalassignment.common.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

public abstract class RestException extends RuntimeException{

    public RestException(String message) {
        super(message);
    }

    public abstract HttpStatus getStatusCode();
    public abstract List<?> getErrors();

}
