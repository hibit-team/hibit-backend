package com.hibit2.hibit2.profile.exception;

import com.hibit2.hibit2.exception.NotFoundException;

public class NotFoundProfileException extends NotFoundException {

    private static final String ERROR_MESSAGE = "존재하지 않는 프로필입니다.";

    public NotFoundProfileException() {
        super(ERROR_MESSAGE);
    }
}
