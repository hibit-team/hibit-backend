package com.hibit2.hibit2.common.builder;


import com.hibit2.hibit2.auth.domain.OAuthToken;
import com.hibit2.hibit2.member.domain.Member;
import com.hibit2.hibit2.member.domain.SocialType;

public final class GivenBuilder {

    private final BuilderSupporter bs;
    private Member member;

    public GivenBuilder(final BuilderSupporter bs) {
        this.bs = bs;
    }

    public GivenBuilder 회원_가입을_한다(final String email, final String name,
                                  final String profile) {
        Member member = new Member(email, name, profile,SocialType.GOOGLE);
        this.member = bs.memberRepository().save(member);
        OAuthToken oAuthToken = new OAuthToken(this.member, "asd");
        bs.oAuthTokenRepository().save(oAuthToken);
        return this;
    }

    public Member 회원() {
        return member;
    }
}
