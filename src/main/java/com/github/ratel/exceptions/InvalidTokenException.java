package com.github.ratel.exceptions;

import lombok.Getter;

@Getter
public class InvalidTokenException extends RuntimeException {

    private String message;

    public InvalidTokenException(String message) {
        super(message);
        this.message = message;
    }
}
