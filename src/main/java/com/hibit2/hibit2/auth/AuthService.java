package com.hibit2.hibit2.auth;

import com.hibit2.hibit2.auth.dto.TokenResponse;
import com.hibit2.hibit2.infrastructure.oauth.endpoint.OAuthEndpoint;
import com.hibit2.hibit2.infrastructure.client.OAuthClient;
import com.hibit2.hibit2.infrastructure.dto.OAuthMember;

import org.springframework.stereotype.Service;

@Service
public class AuthService {


    private final OAuthEndpoint oAuthEndpoint;
    private final OAuthClient oAuthClient;


    public AuthService(final OAuthEndpoint oAuthEndpoint, final OAuthClient oAuthClient) {
        this.oAuthEndpoint = oAuthEndpoint;
        this.oAuthClient = oAuthClient;
    }

    public String generateGoogleLink() {
            return oAuthEndpoint.generate();
    }

    public TokenResponse generateTokenWithCode(final String code) {
        OAuthMember oAuthMember = oAuthClient.getOAuthMember(code);
        return null;
    }
}