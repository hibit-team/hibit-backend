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


@Table(name = "members")
@Entity
public class Member {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-z0-9._-]+@[a-z]+[.]+[a-z]{2,3}$");
    private static final int MAX_DISPLAY_NAME_LENGTH = 100;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nickname", nullable = true)
    @Schema(description = "닉네임", example = "아아아")
    private String nickname;

    @Column(name = "email", nullable = false)
    @Schema(description = "이메일", example = "teamhibit@gmail.com")
    private String email;

    @Column(name = "display_name", nullable = false)
    private String displayName;

    @Column(name = "profile_image_url", nullable = false)
    private String profileImageUrl;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "social_type", nullable = false)
    @Schema(description = "소셜 로그인 유형", example = "GOOGLE")
    private SocialType socialType;

    @Column(name = "mainimg", nullable = true)
    @Schema(description = "대표 이미지", example = "http")
    private String mainImg;

    @Column(nullable = false, columnDefinition = "integer default 0")
    @Schema(description = "신고 수", example = "0")
    private int report;

    @Column(nullable = false, columnDefinition = "boolean default false")
    @Schema(description = "프로필 작성", example = "0")
    private boolean isProfile;


    protected Member() {
    }

    public Member(String email, String displayName, String profileImageUrl, SocialType socialType) {

        validateEmail(email);
        validateDisplayName(displayName);

        this.email = email;
        this.displayName = displayName;
        this.profileImageUrl = profileImageUrl;
        this.socialType = socialType;
    }

    private void validateEmail(final String email) {
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        if(!matcher.matches()) {
            throw new InvalidMemberException("이메일 형식이 올바르지 않습니다.");
        }
    }

    private void validateDisplayName(final String displayName) {
        if (displayName.isEmpty() || displayName.length() > MAX_DISPLAY_NAME_LENGTH) {
            throw new InvalidMemberException(String.format("이름은 1자 이상 1자 %d이하여야 합니다.", MAX_DISPLAY_NAME_LENGTH));
        }
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {this.nickname = nickname;}

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setMainImg(String mainImg) {this.mainImg = mainImg;}
    public String getMainImg() {
        return mainImg;
    }

    public void updateMainImg(String mainImg) {
        this.mainImg = mainImg;
    }
    public void AddReport(){this.report += 1;}
    public boolean getIsprofile(){ return isProfile;}
    public void updateIsprofile(){this.isProfile=true;}
}
