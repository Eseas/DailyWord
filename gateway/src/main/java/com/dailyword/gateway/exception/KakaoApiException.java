package com.dailyword.gateway.exception;

public class KakaoApiException extends RuntimeException {

    private final int statusCode;

    public KakaoApiException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}