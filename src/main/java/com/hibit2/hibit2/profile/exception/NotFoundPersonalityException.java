package com.hibit2.hibit2.profile.exception;

public class NotFoundPersonalityException extends RuntimeException {

    public NotFoundPersonalityException(final String message) {
        super(message);
    }

    public NotFoundPersonalityException() {
        this("존재하지 않는 성격 유형입니다.");
    }
}
