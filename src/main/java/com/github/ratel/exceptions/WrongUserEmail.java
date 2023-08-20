package com.github.ratel.exceptions;

import com.github.ratel.exceptions.statuscode.StatusCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
public class WrongUserEmail extends RuntimeException {

    private Integer statusCode = HttpStatus.BAD_REQUEST.value();
    private String message = "Неправильный адрес электронной почты";

    public WrongUserEmail(StatusCode error) {
        this.statusCode = error.getCode();
        this.message = error.getMessage();
    }

    public WrongUserEmail(Integer statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public WrongUserEmail(String message) {
        super(message);
        this.message = message;
    }

}
