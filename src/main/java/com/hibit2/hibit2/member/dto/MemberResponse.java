package com.hibit2.hibit2.member.dto;

import com.hibit2.hibit2.member.domain.Member;
import com.hibit2.hibit2.member.domain.SocialType;

public class MemberResponse {

    private Long id;
    private String email;
    private SocialType socialType;

    public MemberResponse(final Long id, final String email, final SocialType socialType) {
        this.id = id;
        this.email = email;
        this.socialType = socialType;
    }

    public MemberResponse(final Member member) {
        this(member.getId(), member.getEmail(), member.getSocialType());
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public SocialType getSocialType() {
        return socialType;
    }
}
