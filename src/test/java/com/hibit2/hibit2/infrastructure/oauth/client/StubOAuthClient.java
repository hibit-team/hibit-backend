package com.hibit2.hibit2.infrastructure.oauth.client;

import com.hibit2.hibit2.infrastructure.client.OAuthClient;
import com.hibit2.hibit2.infrastructure.dto.OAuthMember;

public class StubOAuthClient implements OAuthClient {

    @Override
    public OAuthMember getOAuthMember(final String code) {
        return new OAuthMember("Fake Email", "Fake gender", 28, "Fake email", "Fake Profile Image Url");
    }
}