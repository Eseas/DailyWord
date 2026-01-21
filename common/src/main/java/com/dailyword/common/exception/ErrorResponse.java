package com.dailyword.common.exception;

import com.dailyword.common.response.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ErrorResponse {

    private boolean success;
    private int code;
    private String message;
    private String exceptionType;

    private ErrorResponse(boolean success, int code, String message, String exceptionType) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.exceptionType = exceptionType;
    }

    public static ErrorResponse of(BusinessException e) {
        return new ErrorResponse(false, e.getCode(), e.getMessage(), "BusinessException");
    }

    public static ErrorResponse of(ErrorCode errorCode) {
        return new ErrorResponse(false, errorCode.getCode(), errorCode.getMessage(), "BusinessException");
    }

    public static ErrorResponse of(int code, String message) {
        return new ErrorResponse(false, code, message, "Exception");
    }

    public boolean isBusinessException() {
        return "BusinessException".equals(exceptionType);
    }
}
