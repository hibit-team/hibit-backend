package com.hibit2.hibit2.auth.domain;

import com.hibit2.hibit2.global.config.BaseTimeEntity;
import com.hibit2.hibit2.member.domain.Member;

import javax.persistence.*;
import java.util.Objects;

@Table(name = "oauth_tokens")
@Entity
public class OAuthToken extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "members_id", nullable = false)
    private Member member;
    @Column(name = "refresh_token", nullable = false)
    private String refreshToken;

    protected OAuthToken() {
    }

    public OAuthToken(final Member member, final String refreshToken) {
        this.member = member;
        this.refreshToken = refreshToken;
    }

    public void change(final String refreshToken) {
        if (!Objects.isNull(refreshToken)) {
            this.refreshToken = refreshToken;
        }
    }

    public Long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
