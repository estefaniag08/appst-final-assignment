package dev.applaudostudios.examples.finalassignment.common.exception;

import org.springframework.http.HttpStatus;

public interface RestException{
    HttpStatus getStatusCode();
}
