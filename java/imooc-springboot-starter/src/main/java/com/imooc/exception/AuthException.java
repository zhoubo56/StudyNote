package com.imooc.exception;

import org.springframework.http.HttpStatus;

public class AuthException extends Exception {
    public HttpStatus httpStatus;

    public AuthException(String message) {
        super(message);
    }

    public AuthException(HttpStatus status, String message) {
        super(message);
        httpStatus = status;
    }

    public static AuthException Unauthorized(String message) {
        return new AuthException(HttpStatus.UNAUTHORIZED, message);
    }

    public static AuthException PaymentRequired(String message) {
        return new AuthException(HttpStatus.PAYMENT_REQUIRED, message);
    }

    public static AuthException Forbidden(String message) {
        return new AuthException(HttpStatus.FORBIDDEN, message);
    }
}
