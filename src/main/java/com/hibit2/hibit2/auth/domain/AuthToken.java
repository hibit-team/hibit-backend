package com.hibit2.hibit2.auth.domain;

public class AuthToken {
    private String accessToken;
    private String refreshToken;

    public AuthToken(final String accessToken, final String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
