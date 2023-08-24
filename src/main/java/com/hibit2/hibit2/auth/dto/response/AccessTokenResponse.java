package com.hibit2.hibit2.auth.dto.response;

public class AccessTokenResponse {

    private Long id;
    private String accessToken;

    private int isProfileRegistered;

    private AccessTokenResponse() {
    }

    public AccessTokenResponse(Long id, String accessToken, int isProfileRegistered) {
        this.id = id;
        this.accessToken = accessToken;
        this.isProfileRegistered = isProfileRegistered;
    }

    public Long getId() {
        return id;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public int getIsProfileRegistered() {
        return isProfileRegistered;
    }
}
