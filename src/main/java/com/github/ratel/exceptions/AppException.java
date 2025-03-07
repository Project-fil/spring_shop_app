package com.github.ratel.exceptions;

import lombok.Getter;

@Getter
public class AppException extends RuntimeException {

    public AppException(String message) {
        super(message);
    }

    public AppException(String format, Object ... params) {
        super(String.format(format, params));
    }
}
