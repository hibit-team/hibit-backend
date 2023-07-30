package com.hibit2.hibit2.auth.support;

import com.hibit2.hibit2.auth.dto.OAuthMember;
import com.hibit2.hibit2.auth.dto.response.OAuthAccessTokenResponse;

public interface OAuthClient {
    OAuthMember getOAuthMember(final String code, final String redirectUri);

    OAuthAccessTokenResponse getAccessToken(final String refreshToken);
}
