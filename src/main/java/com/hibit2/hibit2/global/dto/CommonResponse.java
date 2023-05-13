package com.hibit2.hibit2.global.dto;

public class CommonResponse<T> {

    private T data;

    private CommonResponse() {
    }

    public CommonResponse(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }
}
