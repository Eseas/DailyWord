package com.dailyword.common.response;

public class APIResponse<T> {
    int code;
    String message;
    T data;

    public T getData() {
        return data;
    }

    private APIResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> APIResponse<T> success(int code, T data) {
        return new APIResponse<T>(code, "success", data);
    }

    public static <T> APIResponse<T> success(T data) {
        return new APIResponse<T>(200, "success", data);
    }

    public static <T> APIResponse<T> success() {
        return new APIResponse<T>(200, "success", null);
    }

    public static <T> APIResponse<T> error(int code, String message) {
        return new APIResponse<T>(code, message, null);
    }
}
