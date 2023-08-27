package com.hibit2.hibit2.member.exception;

public class InvalidMemberException extends RuntimeException{

    public InvalidMemberException(final String message) {
        super();
    }
    public InvalidMemberException() {
        this("잘못된 회원의 정보입니다.");
    }
}
