package com.ddd.tooda.common.exception;

public class BadRequestException extends RuntimeException{
    public BadRequestException(String msg) {
        super(msg);
    }
    public BadRequestException() {
        super("잘못된 요청 입니다.");
    }
}
