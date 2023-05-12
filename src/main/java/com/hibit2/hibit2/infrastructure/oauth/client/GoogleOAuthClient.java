package com.hibit2.hibit2.infrastructure.oauth.client;

import org.springframework.stereotype.Component;

import com.hibit2.hibit2.infrastructure.client.OAuthClient;
import com.hibit2.hibit2.infrastructure.dto.OAuthMember;

@Component
public class GoogleOAuthClient implements OAuthClient {
    @Override
    public OAuthMember getOAuthMember(final String code) {
        return null;
    }
}
