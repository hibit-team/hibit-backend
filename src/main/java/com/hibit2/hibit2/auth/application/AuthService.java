package com.hibit2.hibit2.auth.application;

import com.hibit2.hibit2.auth.domain.AuthAccessToken;
import com.hibit2.hibit2.auth.domain.AuthToken;
import com.hibit2.hibit2.auth.domain.OAuthToken;
import com.hibit2.hibit2.auth.dto.request.TokenRenewalRequest;
import com.hibit2.hibit2.auth.dto.response.AccessAndRefreshTokenResponse;
import com.hibit2.hibit2.auth.dto.response.AccessTokenResponse;
import com.hibit2.hibit2.auth.event.MemberSavedEvent;
import com.hibit2.hibit2.auth.domain.OAuthTokenRepository;
import com.hibit2.hibit2.member.domain.Member;
import com.hibit2.hibit2.member.repository.MemberRepository;
import com.hibit2.hibit2.auth.dto.OAuthMember;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class AuthService {


    private final MemberRepository memberRepository;
    private final OAuthTokenRepository oAuthTokenRepository;

    private final TokenCreator tokenCreator;
    private final ApplicationEventPublisher eventPublisher;


    public AuthService(final MemberRepository memberRepository, final OAuthTokenRepository oAuthTokenRepository,
                       final TokenCreator tokenCreator, final ApplicationEventPublisher eventPublisher) {
        this.memberRepository = memberRepository;
        this.oAuthTokenRepository = oAuthTokenRepository;
        this.tokenCreator = tokenCreator;
        this.eventPublisher = eventPublisher;
    }
    @Transactional
    public AccessTokenResponse generateAccessAndRefreshToken(final OAuthMember oAuthMember) {
        Member foundMember = findMember(oAuthMember);

        OAuthToken oAuthToken = getOAuthToken(oAuthMember, foundMember);
        oAuthToken.change(oAuthMember.getRefreshToken());

        AuthAccessToken authToken = tokenCreator.createAuthAccessToken(foundMember.getId());

        return new AccessTokenResponse(authToken.getId(), authToken.getAccessToken(), authToken.getIsProfileRegistered());
    }

    private OAuthToken getOAuthToken(final OAuthMember oAuthMember, final Member member) {
        Long memberId = member.getId();
        if (oAuthTokenRepository.existsByMemberId(memberId)) {
            return oAuthTokenRepository.getByMemberId(memberId);
        }
        return oAuthTokenRepository.save(new OAuthToken(member, oAuthMember.getRefreshToken()));
    }

    private Member findMember(final OAuthMember oAuthMember) {
        String email = oAuthMember.getEmail();
        if (memberRepository.existsByEmail(email)) {
            return memberRepository.getByEmail(email);
        }
        return saveMember(oAuthMember);
    }

    private Member saveMember(final OAuthMember oAuthMember) {
        Member savedMember = memberRepository.save(oAuthMember.toMember());
        eventPublisher.publishEvent(new MemberSavedEvent(savedMember.getId()));
        return savedMember;
    }

    public AccessAndRefreshTokenResponse generateAccessToken(final TokenRenewalRequest tokenRenewalRequest) {
        String refreshToken = tokenRenewalRequest.getRefreshToken();
        AuthToken authToken = tokenCreator.renewAuthToken(refreshToken);
        return new AccessAndRefreshTokenResponse(authToken.getId(), authToken.getAccessToken(), authToken.getRefreshToken());
    }

    public Long extractMemberId(final String accessToken) {
        Long memberId = tokenCreator.extractPayLoad(accessToken);
        memberRepository.validateExistById(memberId);
        return memberId;
    }
}