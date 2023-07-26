package com.hibit2.hibit2.profile.domain;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import javax.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hibit2.hibit2.member.domain.Member;
import com.hibit2.hibit2.profile.exception.InvalidProfileInfoException;

@Table(name = "profiles")
@Entity
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer"})
    @JoinColumn(name = "member_idx")
    private Member member;

    @Column(name = "nickname", length = 20, nullable = false)
    @Schema(description = "닉네임", example = "히빗")
    private String nickname;

    @Column(name = "age", nullable = false)
    @Schema(description = "나이", example = "20")
    private int age;

    @Column(name = "gender", nullable = false)
    @Schema(description = "성별", example = "남성")
    private int gender;

    @Column(name = "personality", nullable = false)
    @Schema(description = "본인의 성격을 골라주세요.(최대 5개)", example = "[\"유머있는\", \"낙천적인\", \"외향적인\"]")
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = PersonalityType.class)
    private List<PersonalityType> personality;

    @Column(name ="introduce", length = 200, nullable = false)
    @Schema(description = "메이트에게 자신을 소개해주세요", example = "안녕하세요 저는 20살 남성 히빗이라고 합니다.")
    private String introduce;

    @Column(name = "main_img", length = 100, nullable = false)
    @Schema(description = "나의 대표사진", example = "http://hibitbucket")
    private String mainImg;

    @ElementCollection
    @Schema(description = "나머지 이미지 url 리스트", example = "[\"http://hibitbucket1\", \"http://hibitbucket2\"]" )
    private List<String> subImg;


    @Column(name = "job", length = 50, nullable = false)
    @Schema(description = "직업 혹은 학교", example = "College student")
    private String job;

    @Column(name = "address_city", nullable = false)
    @Schema(description = "시/도", example = "서울")
    @Enumerated(EnumType.STRING)
    private AddressCity addressCity;

    @Column(name = "address_distinct", nullable = false)
    @Schema(description = "시/군/구", example = "서울 용산구")
    @Enumerated(EnumType.STRING)
    private AddressDistinct addressDistinct;

    @Column(name = "ban")
    @Schema(description = "차단 횟수", defaultValue = "0")
    private int ban;

    protected  Profile() {
    }

    @Builder
    public Profile(Member member, String nickname, int age, int gender, List<PersonalityType> personality,
        String introduce, String mainImg, List<String> subImg, String job, AddressCity addressCity,
        AddressDistinct addressDistinct) {
        this.member = member;
        this.nickname = nickname;
        this.age = age;
        this.gender = gender;
        this.personality = personality;
        this.introduce = introduce;
        this.mainImg = mainImg;
        this.subImg = subImg;
        this.job = job;
        this.addressCity = addressCity;
        this.addressDistinct = addressDistinct;
    }

    public Member getMember() {
        return member;
    }

    public Long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public int getAge() {
        return age;
    }

    public int getGender() {
        return gender;
    }

    public List<PersonalityType> getPersonality() {
        return personality;
    }

    public String getIntroduce() {
        return introduce;
    }

    public String getMainImg() {
        return mainImg;
    }

    public List<String> getSubImg() {
        return subImg;
    }

    public String getJob() {
        return job;
    }

    public AddressCity getAddressCity() {
        return addressCity;
    }

    public AddressDistinct getAddressDistinct() {
        return addressDistinct;
    }

    public int getBan() {
        return ban;
    }

    public void modifyProfile(String nickname, int age, int gender, List<PersonalityType> personality, String introduce
                        , String mainImg, List<String> subImg, String job, AddressCity addressCity, AddressDistinct addressDistinct) {
        validateProfile(nickname, age, gender, personality, introduce, mainImg, subImg, job, addressCity, addressDistinct);
        this.nickname = nickname.trim();
        this.age = age;
        this.gender = gender;
        this.personality = personality;
        this.introduce = introduce.trim();
        this.mainImg = mainImg.trim();
        this.subImg = subImg;
        this.job = job.trim();
        this.addressCity = addressCity;
        this.addressDistinct = addressDistinct;
    }

    private void validateProfile(String nickname, int age, int gender, List<PersonalityType> personality, String introduce
        , String mainImg, List<String> subImg, String job, AddressCity addressCity, AddressDistinct addressDistinct) {
        if((nickname == null || nickname.isBlank())
            || age < 0 || gender < 0
            || personality == null || personality.isEmpty()
            || introduce == null || introduce.isBlank()) {
        } throw new InvalidProfileInfoException();
    }
}
