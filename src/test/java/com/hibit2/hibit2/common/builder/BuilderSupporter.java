package com.hibit2.hibit2.common.builder;


import com.hibit2.hibit2.auth.domain.OAuthTokenRepository;
import com.hibit2.hibit2.member.repository.MemberRepository;
import org.springframework.stereotype.Component;

@Component
public class BuilderSupporter {

    private final MemberRepository memberRepository;
    private final OAuthTokenRepository oAuthTokenRepository;

    public BuilderSupporter(final MemberRepository memberRepository,
                            final OAuthTokenRepository oAuthTokenRepository) {
        this.memberRepository = memberRepository;
        this.oAuthTokenRepository = oAuthTokenRepository;
    }

    public MemberRepository memberRepository() {
        return memberRepository;
    }

    public OAuthTokenRepository oAuthTokenRepository() {
        return oAuthTokenRepository;
    }

}
