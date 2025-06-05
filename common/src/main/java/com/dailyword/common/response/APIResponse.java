package com.dailyword.common.response;

public class APIResponse<T> {
    boolean success;
    int code;
    String message;
    T data;

    public boolean isSuccess() {
        return success;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    private APIResponse(boolean success, int code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> APIResponse<T> success(int code, T data) {
        return new APIResponse<T>(true, code, "success", data);
    }

    public static <T> APIResponse<T> success(T data) {
        return new APIResponse<T>(true,200, "success", data);
    }

    public static <T> APIResponse<T> success() {
        return new APIResponse<T>(true,200, "success", null);
    }

    public static <T> APIResponse<T> error(int code, String message) {
        return new APIResponse<T>(true, code, message, null);
    }
}
