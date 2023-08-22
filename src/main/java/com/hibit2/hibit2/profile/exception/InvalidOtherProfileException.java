package com.hibit2.hibit2.profile.exception;

public class InvalidOtherProfileException extends RuntimeException {
    public InvalidOtherProfileException(final String message) {
        super(message);
    }

    public InvalidOtherProfileException() {
        this("자신의 프로필은 다른 방식으로 조회하세요.");
    }
}
