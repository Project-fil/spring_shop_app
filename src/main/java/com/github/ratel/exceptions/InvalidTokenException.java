package com.github.ratel.exceptions;

import com.github.ratel.exceptions.statuscode.StatusCode;
import lombok.Getter;

@Getter
public class InvalidTokenException extends RuntimeException {

    private Integer statusCode;
    private String message;

    public InvalidTokenException(Integer statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public InvalidTokenException(StatusCode error) {
        this.statusCode = error.getCode();
        this.message = error.getMessage();
    }

    public InvalidTokenException(String message) {
        super(message);
        this.message = message;
    }
}
