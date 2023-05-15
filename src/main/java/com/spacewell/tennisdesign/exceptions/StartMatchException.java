package com.spacewell.tennisdesign.exceptions;

import lombok.Getter;

@Getter
public class StartMatchException extends RuntimeException {

    private final ExceptionType exceptionType;

    public StartMatchException(String message, ExceptionType exceptionType) {
        super(message);
        this.exceptionType = exceptionType;
    }

}
