package com.hibit2.hibit2.profile.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NicknameAlreadyTakenException extends RuntimeException {
    public NicknameAlreadyTakenException(final String message) {
        super(message);
    }

    public NicknameAlreadyTakenException() {
        this("이미 사용중인 닉네임 입니다.");
    }
}
