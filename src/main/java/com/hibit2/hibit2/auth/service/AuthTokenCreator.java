package com.hibit2.hibit2.auth.service;

import com.hibit2.hibit2.auth.domain.AuthToken;
import com.hibit2.hibit2.auth.repository.TokenRepository;
import org.springframework.stereotype.Component;

@Component
public class AuthTokenCreator implements TokenCreator {
    private final TokenProvider tokenProvider;

    private final TokenRepository tokenRepository;

    public AuthTokenCreator(TokenProvider tokenProvider, TokenRepository tokenRepository) {
        this.tokenProvider = tokenProvider;
        this.tokenRepository = tokenRepository;
    }

    public AuthToken createAuthToken(final Long memberId) {
        String accessToken = tokenProvider.createAccessToken(String.valueOf(memberId));
        String refreshToken = createRefreshToken(memberId);
        return new AuthToken(accessToken, refreshToken);
    }

    private String createRefreshToken(final Long memberId) {
        if (tokenRepository.exist(memberId)) {
            return tokenRepository.getToken(memberId);
        }
        String refreshToken = tokenProvider.createRefreshToken(String.valueOf(memberId));
        return tokenRepository.save(memberId, refreshToken);
    }

    public Long extractPayLoad(final String accessToken) {
        tokenProvider.validateToken(accessToken);
        return Long.valueOf(tokenProvider.getPayload(accessToken));
    }
}
