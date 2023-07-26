package com.hibit2.hibit2.auth.exception;

public class InvalidTokenException extends RuntimeException {

    public InvalidTokenException(final String message) {
        super(message);
    }

    public InvalidTokenException() {
        this("유효하지 않은 토큰입니다.");
    }
}
