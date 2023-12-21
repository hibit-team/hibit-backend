package com.hibit2.hibit2.matching.exception;

public class NotFoundMatchingException extends RuntimeException {
    public NotFoundMatchingException(final String message) {
        super(message);
    }

    public NotFoundMatchingException() {
        this("매칭 요청을 찾을 수 없습니다.");
    }
}
