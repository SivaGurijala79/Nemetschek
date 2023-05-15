package com.spacewell.tenissdesign.exceptions;

import lombok.Getter;

@Getter
public class StartMatchException extends RuntimeException {

    private ExceptionType exceptionType;

    public StartMatchException(String message, ExceptionType exceptionType) {
        super(message);
        this.exceptionType = exceptionType;
    }

}
