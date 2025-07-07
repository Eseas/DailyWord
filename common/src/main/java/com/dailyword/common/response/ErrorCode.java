package com.dailyword.common.response;

public enum ErrorCode {
    //------------------------------ module member -----------------------------------------
    NOT_FOUND_MEMBER(2000, "해당 멤버를 찾을 수 없습니다."),
    REQUIRED_REGIST_MEMBER(2001, "해당 회원은 등록해야만 합니다."),
    //------------------------------ module post -------------------------------------------
    NOT_FOUND_POST(3000, "해당 게시글을 찾을 수 없습니다."),
    NOT_YOUR_POST(3001, "할 수 없는 행동입니다."),
    //------------------------------ module comment ----------------------------------------
    NOT_FOUND_COMMENT(4000, "해당 댓글을 찾을 수 없습니다.");


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
