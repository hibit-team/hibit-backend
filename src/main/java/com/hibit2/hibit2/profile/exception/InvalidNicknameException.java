package com.hibit2.hibit2.profile.exception;

public class InvalidNicknameException extends RuntimeException{
    public InvalidNicknameException(final String message) {
        super(message);
    }

    public InvalidNicknameException() {
        this("잘못된 닉네임 정보입니다.");
    }
}
