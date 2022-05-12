package dev.applaudostudios.examples.finalassignment.common.exception.order;

import org.springframework.http.HttpStatus;

import java.util.List;

public class OrderItemNotFoundException extends OrderRelatedException{
    public OrderItemNotFoundException(List<String> listOfErrors) {
        super(listOfErrors);
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.NOT_FOUND;
    }
}
