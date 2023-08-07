package com.hibit2.hibit2.auth.dto.response;

public class AccessAndRefreshTokenResponse {

    private final Long id;
    private final String accessToken;

    private final String refreshToken;

    public AccessAndRefreshTokenResponse(Long id, String accessToken, String refreshToken) {
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
