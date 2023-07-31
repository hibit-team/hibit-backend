package com.hibit2.hibit2.member.domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hibit2.hibit2.member.exception.InvalidMemberException;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Table(name = "members")
@Entity
public class Member {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-z0-9._-]+@[a-z]+[.]+[a-z]{2,3}$");
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email", nullable = false)
    @Schema(description = "이메일", example = "teamhibit@gmail.com")
    private String email;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "social_type", nullable = false)
    @Schema(description = "소셜 로그인 유형", example = "GOOGLE")
    private SocialType socialType;

    protected Member() {
    }

    public  Member(final String email, final SocialType socialType) {
        validateEmail(email);

        this.email = email;
        this.socialType = socialType;
    }

    private void validateEmail(final String email) {
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        if(!matcher.matches()) {
            throw new InvalidMemberException("이메일 형식이 올바르지 않습니다.");
        }
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
