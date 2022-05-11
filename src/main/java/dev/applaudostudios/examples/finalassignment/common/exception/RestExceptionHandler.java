package dev.applaudostudios.examples.finalassignment.common.exception;

import dev.applaudostudios.examples.finalassignment.common.dto.ErrorDto;
import dev.applaudostudios.examples.finalassignment.common.exception.order.OrderRelatedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Optional;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(OrderRelatedException.class)
    public ResponseEntity<ErrorDto> generateResponseException(OrderRelatedException exception){
        ErrorDto error = new ErrorDto(exception.getStatusCode());
        error.setErrors(Optional.ofNullable(exception.getErrors()));
        return new ResponseEntity<>(error, error.getStatusCode());
    }


    public ResponseEntity<ErrorDto> handleRequestException(RequestException exception){
        ErrorDto error = new ErrorDto(exception.getStatusCode());
        error.setErrors(Optional.ofNullable(exception.getErrors()));
        return new ResponseEntity<>(error, error.getStatusCode());
    }
}
