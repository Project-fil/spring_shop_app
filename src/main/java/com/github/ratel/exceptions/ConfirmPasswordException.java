package com.github.ratel.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
public class ConfirmPasswordException extends RuntimeException {

    private int status = HttpStatus.LOCKED.value();
    private String message = HttpStatus.LOCKED.getReasonPhrase();

    public ConfirmPasswordException(String message) {
        super(message);
        this.message = message;
    }
}
