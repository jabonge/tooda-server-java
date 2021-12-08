package com.ddd.tooda.common.exception;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public class ApiError {

    private final int statusCode;
    private final String message;

    ApiError(Throwable throwable, HttpStatus status) {
        this(throwable.getMessage(),status);
    }

    ApiError(String message, HttpStatus status) {
        this.message = message;
        this.statusCode = status.value();
    }

}
