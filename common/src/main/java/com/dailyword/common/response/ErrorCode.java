package com.dailyword.common.response;

public enum ErrorCode {
    NOT_FOUND_MEMBER(404, "해당 멤버를 찾을 수 없습니다.");

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
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
