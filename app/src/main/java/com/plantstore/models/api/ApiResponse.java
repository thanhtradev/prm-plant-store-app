package com.plantstore.models.api;

public abstract class ApiResponse<T> {
    private boolean result;
    private T data;
    private String message;

    public ApiResponse() {
    }

    public boolean isResult() {
        return result;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
