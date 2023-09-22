package com.github.ratel.exceptions;

import com.github.ratel.exceptions.statuscode.StatusCode;

public class NotValidException extends RuntimeException {

    private Integer statusCode;
    private String message;

    public NotValidException(StatusCode error) {
        this.statusCode = error.getCode();
        this.message = error.getMessage();
    }

    public NotValidException(Integer statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public NotValidException(String massage) {super(massage);}

}
