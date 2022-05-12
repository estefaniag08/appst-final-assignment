package dev.applaudostudios.examples.finalassignment.common.exception.order;

import org.springframework.http.HttpStatus;

import java.util.List;

public class OrderNotFoundException extends OrderRelatedException {
    public OrderNotFoundException(List<String> listOfErrors) {
        super(listOfErrors);
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.NOT_FOUND;
    }
}
