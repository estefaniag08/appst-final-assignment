package dev.applaudostudios.examples.finalassignment.common.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

public interface RestException{
    HttpStatus getStatusCode();
    List<?> getErrors();
}
