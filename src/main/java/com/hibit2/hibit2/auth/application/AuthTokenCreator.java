package com.hibit2.hibit2.auth.application;

import com.hibit2.hibit2.auth.domain.AuthAccessToken;
import com.hibit2.hibit2.auth.domain.AuthToken;
import com.hibit2.hibit2.auth.domain.TokenRepository;
import com.hibit2.hibit2.profile.domain.Profile;
import com.hibit2.hibit2.profile.repository.ProfileRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthTokenCreator implements TokenCreator {
    private final TokenProvider tokenProvider;
    private final TokenRepository tokenRepository;
    private final AuthTokenResponseHandler authTokenResponseHandler;
    private final ProfileRepository profileRepository;

    public AuthTokenCreator(final TokenProvider tokenProvider, final TokenRepository tokenRepository
            , final AuthTokenResponseHandler authTokenResponseHandler, ProfileRepository profileRepository) {
        this.tokenProvider = tokenProvider;
        this.tokenRepository = tokenRepository;
        this.authTokenResponseHandler = authTokenResponseHandler;
        this.profileRepository = profileRepository;
    }

    public AuthToken createAuthToken(final Long memberId) {
        Long id =  memberId;
        String accessToken = tokenProvider.createAccessToken(String.valueOf(memberId));
        String refreshToken = createRefreshToken(memberId);

        return new AuthToken(id, accessToken, refreshToken);
    }

    @Override
    public AuthAccessToken createAuthAccessToken(Long memberId) {
        Long id =  memberId;
        String accessToken = tokenProvider.createAccessToken(String.valueOf(memberId));
        String refreshToken = createRefreshToken(memberId);
        int isProfileRegistered = isProfileRegistered(memberId);

        // 클라이언트로 리프레시 토큰 값을 전달하는 부분
        authTokenResponseHandler.setRefreshTokenCookie(refreshToken);

        return new AuthAccessToken(id, accessToken, isProfileRegistered);
    }

    private int isProfileRegistered(Long memberId) {
        // 데이터베이스에서 memberId를 이용하여 회원의 프로필 정보를 조회
        // 조회한 프로필 정보가 존재하면 true를 반환, 없으면 false를 반환
        Optional<Profile> profile = profileRepository.findByMemberId(memberId);
        return profile.isPresent() ? 1 : 0;
    }

    private String createRefreshToken(final Long memberId) {
        if (tokenRepository.exist(memberId)) {
            return tokenRepository.getToken(memberId);
        }
        String refreshToken = tokenProvider.createRefreshToken(String.valueOf(memberId));
        return tokenRepository.save(memberId, refreshToken);
    }
    public AuthAccessToken renewAuthToken(final String refreshToken) {
        tokenProvider.validateToken(refreshToken);
        Long memberId = Long.valueOf(tokenProvider.getPayload(refreshToken));

        String accessTokenForRenew = tokenProvider.createAccessToken(String.valueOf(memberId));
        String refreshTokenForRenew = tokenRepository.getToken(memberId);
        int isProfileRegistered = isProfileRegistered(memberId);

        // 클라이언트로 리프레시 토큰 값을 전달하는 부분
        authTokenResponseHandler.setRefreshTokenCookie(refreshTokenForRenew);

        AuthAccessToken renewalAuthAccessToken = new AuthAccessToken(memberId, accessTokenForRenew, isProfileRegistered);
        renewalAuthAccessToken.validateHasSameRefreshToken(refreshTokenForRenew, refreshToken);
        return renewalAuthAccessToken;
    }

    public Long extractPayLoad(final String accessToken) {
        tokenProvider.validateToken(accessToken);
        return Long.valueOf(tokenProvider.getPayload(accessToken));
    }
}
