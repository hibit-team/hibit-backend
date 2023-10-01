package com.hibit2.hibit2.member.exception;

public class NotFoundMemberException extends RuntimeException{

    public NotFoundMemberException(final String message) {
        super(message);
    }

    public NotFoundMemberException() {
        this("존재하지 않는 회원입니다.");
    }
}
