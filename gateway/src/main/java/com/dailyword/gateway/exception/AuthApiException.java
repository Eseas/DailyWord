package com.dailyword.gateway.exception;

public class AuthApiException extends RuntimeException {

    private final int statusCode;

    public AuthApiException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
