package dev.applaudostudios.examples.finalassignment.common.exception.service;

import dev.applaudostudios.examples.finalassignment.common.exception.RestException;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

public class CheckoutServiceException extends RuntimeException implements RestException {
    List<String> listOfErrors;
    public CheckoutServiceException(String message) {
        super("Checkout service exception.");
        listOfErrors = new ArrayList<>();
        listOfErrors.add(message);
    }
    //Me obligaron a hacer esto
    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public List<?> getErrors() {
        return this.listOfErrors;
    }
}
