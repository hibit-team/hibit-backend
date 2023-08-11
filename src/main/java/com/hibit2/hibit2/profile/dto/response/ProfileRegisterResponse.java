package com.hibit2.hibit2.profile.dto.response;

import com.hibit2.hibit2.profile.domain.AddressCity;
import com.hibit2.hibit2.profile.domain.AddressDistrict;
import com.hibit2.hibit2.profile.domain.PersonalityType;
import com.hibit2.hibit2.profile.domain.Profile;

import java.util.List;


public class ProfileRegisterResponse {

    private Long id;

    private String nickname;

    private int age;

    private int gender;

    private List<PersonalityType> personality;

    private String introduce;

    private String mainImg;

    private List<String> subImg;

    private String job;

    private AddressCity addressCity;

    private AddressDistrict addressDistrict;

    public ProfileRegisterResponse(Long id, String nickname, int age, int gender, List<PersonalityType> personality, String introduce,
                                   String mainImg, List<String> subImg, String job, AddressCity addressCity, AddressDistrict addressDistrict) {
        this.id = id;
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

    public ProfileRegisterResponse(Profile saveProfile) {
        this.id = saveProfile.getId();
        this.nickname = saveProfile.getNickname();
        this.age = saveProfile.getAge();
        this.gender = saveProfile.getGender();
        this.personality = saveProfile.getPersonality();
        this.introduce = saveProfile.getIntroduce();
        this.mainImg = saveProfile.getMainImg();
        this.subImg = saveProfile.getSubImg();
        this.job = saveProfile.getJob();
        this.addressCity = saveProfile.getAddressCity();
        this.addressDistrict = saveProfile.getAddressDistrict();
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

    public AddressDistrict getAddressDistinct() {
        return addressDistrict;
    }
}
