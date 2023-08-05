package com.hibit2.hibit2.profile.domain;


import com.hibit2.hibit2.global.config.BaseTimeEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import javax.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hibit2.hibit2.member.domain.Member;
import com.hibit2.hibit2.profile.exception.InvalidProfileInfoException;

@Table(name = "profiles")
@Entity
public class Profile extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "members_id")
    private Member member;

    @Column(name = "nickname", length = 20)
    private String nickname;

    @Column(name = "age")
    private int age;

    @Column(name = "gender")
    private int gender;

    @Column(name = "personality")
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = PersonalityType.class)
    private List<PersonalityType> personality;

    @Column(name ="introduce", length = 200)
    private String introduce;

    @Column(name = "main_img", length = 100)
    private String mainImg;

    @ElementCollection
    private List<String> subImg;


    @Column(name = "job", length = 50)
    private String job;

    @Column(name = "address_city")
    @Enumerated(EnumType.STRING)
    private AddressCity addressCity;

    @Column(name = "address_distinct")
    @Enumerated(EnumType.STRING)
    private AddressDistinct addressDistinct;

    @Column(name = "ban")
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


    private void validateProfile(String nickname, int age, int gender, List<PersonalityType> personality, String introduce
            , String mainImg, List<String> subImg, String job, AddressCity addressCity, AddressDistinct addressDistinct) {
        if((nickname == null || nickname.isBlank())
                || age < 0 || gender < 0
                || personality == null || personality.isEmpty()
                || introduce == null || introduce.isBlank()) {
            throw new InvalidProfileInfoException();
        }
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updateAge(int age) {
        this.age = age;
    }

    public void updateGender(int gender) {
        this.gender = gender;
    }

    public void updatePersonality(List<PersonalityType> personality) {
        this.personality = personality;
    }

    public void updateIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public void updateMainImg(String mainImg) {
        this.mainImg = mainImg;
    }

    public void updateSubImg(List<String> subImg) {
        this.subImg =subImg;
    }

    public void updateJob(String job) {
        this.job = job;
    }

    public void updateAddressCity(AddressCity addressCity) {
        this.addressCity = addressCity;
    }

    public void updateAddressDistinct(AddressDistinct addressDistinct) {
        this.addressDistinct = addressDistinct;
    }
}
