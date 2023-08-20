package com.github.ratel.exceptions;

import com.github.ratel.exceptions.statuscode.StatusCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
public class ConfirmPasswordException extends RuntimeException {

    private int status = HttpStatus.LOCKED.value();
    private String message = HttpStatus.LOCKED.getReasonPhrase();

    public ConfirmPasswordException(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public ConfirmPasswordException(StatusCode error) {
        this.status = error.getCode();
        this.message = error.getMessage();
    }

    public ConfirmPasswordException(String message) {
        super(message);
        this.message = message;
    }
}
