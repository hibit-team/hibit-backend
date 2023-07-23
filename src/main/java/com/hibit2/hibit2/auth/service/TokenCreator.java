package com.hibit2.hibit2.auth.service;

import com.hibit2.hibit2.auth.domain.AuthToken;

public interface TokenCreator {
    AuthToken createAuthToken(final Long memberId);

    Long extractPayLoad(final String accessToken);
}
