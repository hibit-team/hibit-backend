package com.hibit2.hibit2.profile.exception;

public class NicknameAlreadyTakenException extends RuntimeException {
    public NicknameAlreadyTakenException(final String message) {
        super(message);
    }
    public NicknameAlreadyTakenException() {
        this("이미 존재하는 닉네임 입니다.");
    }
}
