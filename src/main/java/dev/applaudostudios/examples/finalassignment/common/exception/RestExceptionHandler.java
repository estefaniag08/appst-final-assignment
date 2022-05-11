package dev.applaudostudios.examples.finalassignment.common.exception;

import dev.applaudostudios.examples.finalassignment.common.dto.ErrorDto;
import dev.applaudostudios.examples.finalassignment.common.exception.order.OrderRelatedException;
import dev.applaudostudios.examples.finalassignment.common.exception.service.CheckoutServiceException;
import dev.applaudostudios.examples.finalassignment.common.exception.service.ProductStockOutOfBoundException;
import dev.applaudostudios.examples.finalassignment.common.exception.user.UserRelatedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Optional;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler({OrderRelatedException.class,
                        UserRelatedException.class,
                        CheckoutServiceException.class,
                        RequestException.class,
                        ProductStockOutOfBoundException.class})
    public ResponseEntity<ErrorDto> handleRestException(RestException exception){
        return createResponseForException(exception);
    }

    private ResponseEntity<ErrorDto> createResponseForException(RestException exception){
        ErrorDto error = new ErrorDto(exception.getStatusCode());
        error.setMessage(exception.getMessage());
        error.setErrors(Optional.ofNullable(exception.getErrors()));
        return new ResponseEntity<>(error, error.getStatusCode());
    }
}
