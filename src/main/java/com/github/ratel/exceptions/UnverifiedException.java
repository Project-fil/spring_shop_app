package com.github.ratel.exceptions;

import com.github.ratel.exceptions.statuscode.StatusCode;
import lombok.Getter;

@Getter
public class UnverifiedException extends RuntimeException {

    private final Integer statusCode;
    private final String message;

    public UnverifiedException(StatusCode error) {
        this.statusCode = error.getCode();
        this.message = error.getMessage();
    }
}
