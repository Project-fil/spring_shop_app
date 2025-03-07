package com.github.ratel.exceptions.statuscode;

public enum StatusCode {

    USER_NOT_VERIFIED(403, "User not verified"),
    NOT_FOUND(404, "Not Found"),
    WRONG_USER_EMAIL(400, "Wrong email address");

    private final int code;
    private final String message;

    StatusCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
