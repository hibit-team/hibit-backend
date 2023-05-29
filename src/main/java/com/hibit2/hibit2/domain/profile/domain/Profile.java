package com.hibit2.hibit2.domain.profile.domain;

import com.hibit2.hibit2.domain.member.exception.InvalidMemberException;
import lombok.Getter;

import javax.persistence.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Table(name = "profiles")
@Getter
@Entity
public class Profile {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-z0-9._-]+@[a-z]+[.]+[a-z]{2,3}$");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "matching_no")
    private int matchingNo;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "nickname", length = 10, nullable = false)
    private String nickname;

    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "profile_image_url", length = 254, nullable = false)
    private String profileImageUrl;

    @Column(name = "address_city", length = 254, nullable = false)
    private String addressCity;

    @Column(name = "address_distinct", length = 254, nullable = false)
    private String addressDistinct;

    @Column(name = "style", length = 254, nullable = false)
    private String style;

    @Column(name = "personality", length = 254, nullable = false)
    private String personality;

    @Column(name = "job", length = 20, nullable = false)
    private String job;

    @Column(name ="introduce", length = 200, nullable = false)
    private String introduce;


    protected  Profile() {
    }

    public Profile(final int matchingNo, final String email, final String nickname, final String gender,
                   final String profileImageUrl, final String addressCity, final String addressDistinct, final String style,
                   final String personality, final String job, final String introduce) {
        this.matchingNo = matchingNo;
        this.email = email;
        this.nickname = nickname;
        this.gender = gender;
        this.profileImageUrl = profileImageUrl;
        this.addressCity = addressCity;
        this.addressDistinct = addressDistinct;
        this.style = style;
        this.personality = personality;
        this.job = job;
        this.introduce = introduce;
    }

    private void validateEmail(final String email) {
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        if(!matcher.matches()) {
            throw new InvalidMemberException("이메일 형식이 올바르지 않습니다");
        }
    }
}
