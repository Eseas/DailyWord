package com.dailyword.notification.adapter.out.slack;

public class SlackApiException extends RuntimeException {

    private final String errorCode;

    public SlackApiException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public SlackApiException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = "UNKNOWN";
    }

    public String getErrorCode() {
        return errorCode;
    }
}
