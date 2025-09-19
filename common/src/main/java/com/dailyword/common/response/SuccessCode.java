package com.dailyword.common.response;

public enum SuccessCode {
    //------------------------------ common ----------------------------------------
    SUCCESS(200, "성공"),
    //------------------------------ module version ----------------------------------------
    VERSION_CHECK_SUCCESS(200, "버전 확인 완료"),
    UPDATE_NOT_REQUIRED(200, "최신 버전을 사용 중입니다.");

    private final int code;
    private final String message;

    SuccessCode(int code, String message) {
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