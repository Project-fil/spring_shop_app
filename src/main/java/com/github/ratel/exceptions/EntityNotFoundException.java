package com.github.ratel.exceptions;

import com.github.ratel.exceptions.statuscode.StatusCode;
import lombok.Getter;

@Getter
public class EntityNotFoundException extends RuntimeException {

    private Integer statusCode;
    private String message;

    public EntityNotFoundException(StatusCode error) {
        this.statusCode = error.getCode();
        this.message = error.getMessage();
    }

    public EntityNotFoundException(Integer statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public EntityNotFoundException(String massage) {super(massage);}

}
