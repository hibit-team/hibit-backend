package com.hibit2.hibit2.auth.dto.response;

public class AccessAndRefreshTokenResponse {

    private final String accessToken;

    private final String refreshToken;

    public AccessAndRefreshTokenResponse(String accessToken, String refreshToken) {
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
