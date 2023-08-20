package com.hibit2.hibit2.profile.exception;

public class InvalidPersonalityException extends RuntimeException {
    public InvalidPersonalityException(final String message) {
        super(message);
    }
    public InvalidPersonalityException() {
        this("최대 5개의 성격만 선택할 수 있습니다.");
    }

}
