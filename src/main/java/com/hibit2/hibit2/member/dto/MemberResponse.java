package com.hibit2.hibit2.member.dto;

import com.hibit2.hibit2.member.domain.Member;
import com.hibit2.hibit2.member.domain.SocialType;

public class MemberResponse {

    private Long id;
    private String email;

    private String displayName;
    private String profileImageUrl;
    private SocialType socialType;

    public MemberResponse(Long id, String email, String displayName, String profileImageUrl, SocialType socialType) {
        this.id = id;
        this.email = email;
        this.displayName = displayName;
        this.profileImageUrl = profileImageUrl;
        this.socialType = socialType;
    }

    public MemberResponse(final Member member) {
        this(member.getId(), member.getEmail(), member.getDisplayName(), member.getProfileImageUrl(),
                member.getSocialType());
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public SocialType getSocialType() {
        return socialType;
    }
}
