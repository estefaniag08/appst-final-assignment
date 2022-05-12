package dev.applaudostudios.examples.finalassignment.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;

import java.util.List;

public class RequestException extends RestException{
    private List<ObjectError> listOfErrors;

    public RequestException(String message, List<ObjectError> listOfErrors) {
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
