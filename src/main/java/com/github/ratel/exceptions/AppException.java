package com.github.ratel.exceptions;

import lombok.Getter;

@Getter
public class AppException extends RuntimeException {

    public AppException(String message) {
        super(message);
    }
}
