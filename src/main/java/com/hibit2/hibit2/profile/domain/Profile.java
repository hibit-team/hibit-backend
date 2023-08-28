package com.hibit2.hibit2.profile.domain;


import com.hibit2.hibit2.global.config.BaseTimeEntity;
import lombok.Builder;

import javax.persistence.*;
import java.util.List;

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

    @Column(name = "introduce", length = 200)
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
    private AddressDistrict addressDistrict;

    @Column(name = "ban")
    private int ban;

    @Column(name = "job_visible")
    private boolean jobVisible;

    @Column(name = "sub_img_visible")
    private boolean subImgVisible;

    @Column(name = "address_visible")
    private boolean addressVisible;

    protected Profile() {
    }

    @Builder
    public Profile(Member member, String nickname, int age, int gender, List<PersonalityType> personality,
                   String introduce, String mainImg, List<String> subImg, String job, AddressCity addressCity,
                   AddressDistrict addressDistrict) {
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
        this.addressDistrict = addressDistrict;
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

    public AddressDistrict getAddressDistrict() {
        return addressDistrict;
    }

    public int getBan() {
        return ban;
    }

    public boolean isJobVisible() {
        return jobVisible;
    }

    public boolean isSubImgVisible() {
        return subImgVisible;
    }

    public boolean isAddressVisible() {
        return addressVisible;
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
        this.subImg = subImg;
    }

    public void updateJob(String job) {
        this.job = job;
    }

    public void updateAddressCity(AddressCity addressCity) {
        this.addressCity = addressCity;
    }

    public void updateAddressDistinct(AddressDistrict addressDistrict) {
        this.addressDistrict = addressDistrict;
    }
}
