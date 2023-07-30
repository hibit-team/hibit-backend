package com.hibit2.hibit2.infrastructure.oauth.client;

import com.hibit2.hibit2.auth.application.OAuthClient;
import com.hibit2.hibit2.auth.dto.OAuthMember;
import com.hibit2.hibit2.auth.dto.response.OAuthAccessTokenResponse;
import com.hibit2.hibit2.common.OAuthFixtures;

import static com.hibit2.hibit2.common.AuthFixtures.STUB_OAUTH_ACCESS_TOKEN;

public class StubOAuthClient implements OAuthClient {

    @Override
    public OAuthMember getOAuthMember(final String code, final String redirectUri) {
        return OAuthFixtures.parseOAuthMember(code);
    }

    @Override
    public OAuthAccessTokenResponse getAccessToken(final String refreshToken) {
        return new OAuthAccessTokenResponse(STUB_OAUTH_ACCESS_TOKEN);
    }
}