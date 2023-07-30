package com.hibit2.hibit2.infrastructure.oauth.uri;


import com.hibit2.hibit2.auth.application.OAuthUri;

public class StubOAuthUri implements OAuthUri {

    @Override
    public String generate(final String redirectUri) {
        return "https://localhost:3000";
    }
}
