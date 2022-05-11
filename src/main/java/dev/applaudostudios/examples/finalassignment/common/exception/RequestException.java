package dev.applaudostudios.examples.finalassignment.common.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

public class RequestException extends RuntimeException implements RestException{
    private List<String> listOfErrors;

    public RequestException(String message, List<String> listOfErrors) {
        super(message);
        this.listOfErrors = listOfErrors;
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public List<?> getErrors() {
        return this.listOfErrors;
    }
}
