package com.ddd.tooda.common.exception;

import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {
            IllegalArgumentException.class,
            BadRequestException.class,
            MethodArgumentNotValidException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleBadRequest(Exception e) {
        log.error(e.getMessage());
        return new ApiError(e,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {
            NoHandlerFoundException.class,
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFoundRequest(Exception e) {
        log.error(e.getMessage());
        return new ApiError(e,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {
            UnAuthorizedException.class,
            JwtException.class
    })
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiError handleUnAuthorizedError(Exception e) {
        log.error(e.getMessage());
        return new ApiError(e,HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {
            Exception.class,
    })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleInternalServerError(Exception e) {
        log.error(e.getMessage());
        return new ApiError(e,HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
