package com.hibit2.hibit2.infrastructure.oauth.client;

import com.hibit2.hibit2.auth.dto.OAuthMember;
import com.hibit2.hibit2.auth.support.OAuthClient;

public class StubOAuthClient implements OAuthClient {

    @Override
    public OAuthMember getOAuthMember(final String code) {
        return new OAuthMember("fancy.junyongmoon@gmail.com", "남자", 28);
    }
}