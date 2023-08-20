package com.hibit2.hibit2.auth.application;

import com.hibit2.hibit2.auth.domain.AuthAccessToken;
import com.hibit2.hibit2.auth.domain.AuthToken;

public interface TokenCreator {
    AuthToken createAuthToken(final Long memberId);

    AuthAccessToken createAuthAccessToken(final Long memberId);

    AuthAccessToken renewAuthToken(final String outRefreshToken);

    Long extractPayLoad(final String accessToken);
}
