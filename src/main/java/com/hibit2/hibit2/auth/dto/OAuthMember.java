package com.hibit2.hibit2.auth.dto;

import com.hibit2.hibit2.member.domain.Member;
import com.hibit2.hibit2.member.domain.SocialType;

public class OAuthMember {

    private final String email;
    private final String refreshToken;


    public OAuthMember(String email, String refreshToken) {
        this.email = email;
        this.refreshToken = refreshToken;
    }

    public String getEmail() {
        return email;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public Member toMember() {
        return new Member(email, SocialType.GOOGLE);
    }

}