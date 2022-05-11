package dev.applaudostudios.examples.finalassignment.common.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;
@Getter
@Setter
public class ErrorDto {
    private HttpStatus statusCode;
    private String message;
    private Optional<List<?>> errors;

    public ErrorDto(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }

}
