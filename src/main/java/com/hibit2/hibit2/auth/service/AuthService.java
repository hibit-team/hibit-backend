package com.hibit2.hibit2.auth.service;

import com.hibit2.hibit2.auth.domain.AuthToken;
import com.hibit2.hibit2.auth.dto.AccessAndRefreshTokenResponse;
import com.hibit2.hibit2.auth.support.OAuthClient;
import com.hibit2.hibit2.member.domain.Member;
import com.hibit2.hibit2.member.domain.SocialType;
import com.hibit2.hibit2.member.service.MemberService;
import com.hibit2.hibit2.auth.support.OAuthEndpoint;
import com.hibit2.hibit2.auth.dto.OAuthMember;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class AuthService {


    private final OAuthEndpoint oAuthEndpoint;
    private final OAuthClient oAuthClient;

    private final MemberService memberService;

    private final TokenCreator tokenCreator;

    public AuthService(final OAuthEndpoint oAuthEndpoint, final OAuthClient oAuthClient
    , final MemberService memberService, final TokenCreator tokenCreator) {
        this.oAuthEndpoint = oAuthEndpoint;
        this.oAuthClient = oAuthClient;
        this.memberService = memberService;
        this.tokenCreator = tokenCreator;
    }

    public String generateGoogleLink() {
            return oAuthEndpoint.generate();
    }

    @Transactional
    public AccessAndRefreshTokenResponse generateAccessAndRefreshTokenWithCode(final String code) {
        OAuthMember oAuthMember = oAuthClient.getOAuthMember(code);

        String email = oAuthMember.getEmail();

        saveMemberIfNotExists(oAuthMember, email);

        Member foundMember = memberService.findByEmail(email);
        AuthToken authToken = tokenCreator.createAuthToken(foundMember.getId());

        return new AccessAndRefreshTokenResponse(authToken.getAccessToken(), authToken.getRefreshToken());
    }

    private void saveMemberIfNotExists(final OAuthMember oAuthMember, final String email) {
        if (!memberService.existByEmail(email)) {
            memberService.save(generateMemberBy(oAuthMember));
        }
    }

    private Member generateMemberBy(final OAuthMember oAuthMember) {
        return new Member(oAuthMember.getEmail(), SocialType.GOOGLE);
    }
}