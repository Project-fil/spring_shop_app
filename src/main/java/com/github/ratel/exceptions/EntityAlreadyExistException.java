package com.github.ratel.exceptions;

import lombok.Getter;

@Getter
public class EntityAlreadyExistException extends RuntimeException {

    private final String message;

    public EntityAlreadyExistException(String message) {
        super(message);
        this.message = message;
    }

}
