package com.github.ratel.exceptions;

import com.github.ratel.exceptions.statuscode.StatusCode;
import lombok.Getter;

@Getter
public class UnverifiedException extends RuntimeException {

    private Integer statusCode;
    private final String message;

    public UnverifiedException(String message) {
        super(message);
        this.message = message;
    }

    public UnverifiedException(StatusCode error) {
        this.statusCode = error.getCode();
        this.message = error.getMessage();
    }

    public UnverifiedException(Integer statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

}
