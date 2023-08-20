package com.hibit2.hibit2.auth.domain;

import com.hibit2.hibit2.auth.exception.NoSuchTokenException;

public class AuthToken {

    private Long id;
    private String accessToken;
    private String refreshToken;

    public AuthToken(final Long id, final String accessToken, final String refreshToken) {
        this.id = id;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public Long getId() {
        return id;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
