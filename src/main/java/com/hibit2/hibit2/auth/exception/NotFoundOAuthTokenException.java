package com.hibit2.hibit2.auth.exception;

public class NotFoundOAuthTokenException extends RuntimeException {

    public NotFoundOAuthTokenException(final String message) {
        super(message);
    }

    public NotFoundOAuthTokenException() {
        this("존재하지 않는 OAuthToken 입니다.");
    }
}
