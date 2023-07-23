package com.hibit2.hibit2.auth.exception;

public class EmptyAuthorizationHeader extends RuntimeException {

    public EmptyAuthorizationHeader(final String message) {
        super();
    }
    public EmptyAuthorizationHeader() {
        this("Header에 Authorization이 존재하지 않습니다.");
    }
}
