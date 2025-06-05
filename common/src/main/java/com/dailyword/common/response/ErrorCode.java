package com.dailyword.common.response;

public enum ErrorCode {
    NOT_FOUND_MEMBER(2000, "해당 멤버를 찾을 수 없습니다."),
    REQUIRED_REGIST_MEMBER(2001, "해당 회원은 등록해야만 합니다.");


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
