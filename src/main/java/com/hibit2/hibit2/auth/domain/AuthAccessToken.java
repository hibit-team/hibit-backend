package com.hibit2.hibit2.auth.domain;

import com.hibit2.hibit2.auth.exception.NoSuchTokenException;

// 소셜 로그인으로 액세스 토큰 발급과 프로필 여부를 확인하는 클래스
public class AuthAccessToken {

    private Long id;
    private String accessToken;

    private int isProfileRegistered;

    public AuthAccessToken(final Long id, final String accessToken, final int isProfileRegistered) {
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

    public void validateHasSameRefreshToken(final String refreshTokenForRenew , final String otherRefreshToken) {
        if (!refreshTokenForRenew.equals(otherRefreshToken)) {
            throw new NoSuchTokenException("회원의 리프레시 토큰이 아닙니다.");
        }
    }
}
