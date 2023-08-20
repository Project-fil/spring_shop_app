package com.github.ratel.exceptions.statuscode;

public enum StatusCode {

    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401,"Unauthorized"),
    FORBIDDEN(403, "Forbidden"),
    USER_NOT_VERIFIED(403, "User not verified"),
    NOT_FOUND(404, "Not Found"),
    CONFLICT(409, "Conflict"),
    LOCKED(423, "Access Denied"),
    UNSUPPORTED_MEDIA_TYPE(415, "Not enough input data or something wrong with parameters"),
    NOT_LEGAL(451, "Unavailable For Legal Reasons"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
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
