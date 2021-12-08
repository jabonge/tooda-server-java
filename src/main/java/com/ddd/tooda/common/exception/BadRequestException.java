package com.ddd.tooda.common.exception;

public class BadRequestException extends RuntimeException{
    public BadRequestException(String msg) {
        super(msg);
    }
}
