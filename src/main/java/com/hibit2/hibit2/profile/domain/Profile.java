package com.hibit2.hibit2.profile.domain;

import com.hibit2.hibit2.member.exception.InvalidMemberException;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;
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

    @Column(name = "nickname", length = 20, nullable = false)
    private String nickname;

    @Column(name = "gender", nullable = false)
    private int gender;

    @Column(name = "personality")
    private PersonalityType personality;

    @Column(name ="introduce", length = 200, nullable = false)
    private String introduce;

    @Column(name = "main_img", length = 100, nullable = false)
    private String mainImg;

    @Column(name = "job", length = 50, nullable = false)
    private String job;

    @Column(name = "address_city", nullable = false)
    private Enum addressCity;

    @Column(name = "address_distinct", nullable = false)
    private Enum addressDistinct;


    protected  Profile() {
    }

    public Profile(final int matchingNo, final String email, final String nickname, final int gender, final PersonalityType personalityType
    , final String introduce, final String mainImg, final String job, final Enum addressCity, final Enum addressDistinct) {
        this.matchingNo = matchingNo;
        this.email = email;
        this.nickname = nickname;
        this.gender = gender;
        this.personality = personalityType;
        this.introduce = introduce;
        this.mainImg = mainImg;
        this.job = job;
        this.addressCity = addressCity;
        this.addressDistinct = addressDistinct;
    }
}
