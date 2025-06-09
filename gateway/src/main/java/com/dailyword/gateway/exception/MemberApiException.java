package com.dailyword.gateway.exception;

public class MemberApiException extends RuntimeException {

    private final int statusCode;

    public MemberApiException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
