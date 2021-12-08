package com.ddd.tooda.common.exception;

public class UnAuthorizedException extends RuntimeException {

    public UnAuthorizedException(String msg) {
        super(msg);
    }
}
