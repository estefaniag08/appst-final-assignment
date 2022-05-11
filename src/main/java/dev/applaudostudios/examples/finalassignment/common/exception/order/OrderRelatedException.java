package dev.applaudostudios.examples.finalassignment.common.exception.order;

import dev.applaudostudios.examples.finalassignment.common.exception.RestException;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

public class OrderRelatedException extends RestException {
    List<String> listOfErrors;
    public OrderRelatedException( List<String> listOfErrors) {
        super("Order related exception.");
        listOfErrors = new ArrayList<>();
        this.listOfErrors = listOfErrors;
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    @Override
    public List<?> getErrors() {
        return listOfErrors;
    }
}
