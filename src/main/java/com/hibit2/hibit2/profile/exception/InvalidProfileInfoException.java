package com.hibit2.hibit2.profile.exception;

public class InvalidProfileInfoException extends RuntimeException {
    public InvalidProfileInfoException(final String message) {
        super(message);
    }

    public InvalidProfileInfoException() {
        this("필요한 정보를 모두 입력해 주세요.");
    }

}
