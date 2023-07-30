package com.hibit2.hibit2.common;

import com.hibit2.hibit2.auth.dto.OAuthMember;

import java.util.Arrays;
import java.util.NoSuchElementException;

public enum OAuthFixtures {
    관리자("관리자", 관리자()),
    팬시("팬시", 팬시()),

    MEMBER("member authorization code", MEMBER()),
    CREATOR("creator authorization code", CREATOR());

    private String code;
    private OAuthMember oAuthMember;

    OAuthFixtures(final String code, final OAuthMember oAuthMember) {
        this.code = code;
        this.oAuthMember = oAuthMember;
    }

    public static OAuthMember parseOAuthMember(final String code) {
        OAuthFixtures oAuthFixtures = Arrays.stream(values())
                .filter(value -> value.code.equals(code))
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
        return oAuthFixtures.oAuthMember;
    }

    private static OAuthMember 관리자() {
        String 관리자_이메일 = "hibit.admin@gmail.com";
        String 관리자_REFRESH_TOKEN = "aaaaaaaaaa.bbbbbbbbbb.cccccccccc";
        return new OAuthMember(관리자_이메일, 관리자_REFRESH_TOKEN);
    }

    private static OAuthMember 팬시() {
        String 팬시_이메일 = "parang@email.com";
        String 팬시_REFRESH_TOKEN = "aaaaaaaaaa.bbbbbbbbbb.cccccccccc";
        return new OAuthMember(팬시_이메일, 팬시_REFRESH_TOKEN);
    }
    private static OAuthMember MEMBER() {
        String MEMBER_이메일 = "member@email.com";
        String MEMBER_REFRESH_TOKEN = "aaaaaaaaaa.bbbbbbbbbb.ccccccccc";
        return new OAuthMember(MEMBER_이메일, MEMBER_REFRESH_TOKEN);
    }
    private static OAuthMember CREATOR() {
        String CREATOR_이메일 = "creator@email.com";
        String CREATOR_REFRESH_TOKEN = "aaaaaaaaaa.bbbbbbbbbb.ccccccccc";
        return new OAuthMember(CREATOR_이메일, CREATOR_REFRESH_TOKEN);
    }

    public String getCode() {
        return code;
    }

    public OAuthMember getOAuthMember() {
        return oAuthMember;
    }
}
