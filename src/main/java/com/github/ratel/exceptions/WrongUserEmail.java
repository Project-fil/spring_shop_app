package com.github.ratel.exceptions;

import com.github.ratel.exceptions.statuscode.StatusCode;
import lombok.Getter;

@Getter
public class WrongUserEmail extends RuntimeException {

    private Integer statusCode;
    private String message;

    public WrongUserEmail(StatusCode error) {
        this.statusCode = error.getCode();
        this.message = error.getMessage();
    }
}
