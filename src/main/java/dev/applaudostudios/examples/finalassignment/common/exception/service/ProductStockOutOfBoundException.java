package dev.applaudostudios.examples.finalassignment.common.exception.service;

import dev.applaudostudios.examples.finalassignment.common.exception.RestException;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

public class ProductStockOutOfBoundException extends RuntimeException implements RestException {
    List<String> listOfErrors;
    public ProductStockOutOfBoundException(String message) {
        super("Product exception.");
        listOfErrors = new ArrayList<>();
        listOfErrors.add(message);
    }
    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.BAD_REQUEST;
    }
    @Override
    public List<?> getErrors() {
        return listOfErrors;
    }
}
