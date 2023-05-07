package com.hibit2.hibit2.auth.dto;

public class TokenResponse {

    private final String accessToken;

    public TokenResponse(final String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
