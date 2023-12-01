package com.hibit2.hibit2.profile.dto.response;

import java.util.List;

import com.hibit2.hibit2.profile.domain.AddressCity;
import com.hibit2.hibit2.profile.domain.AddressDistrict;
import com.hibit2.hibit2.profile.domain.PersonalityType;
import com.hibit2.hibit2.profile.domain.Profile;

import lombok.Getter;

@Getter
public class ProfileResponse {

    // 필수 노출 정보
    private String nickname;
    private int gender;
    private List<PersonalityType> personality;
    private String introduce;
    private String mainImg;
    private List<String> subImg;

    // 선택 노출 정보
    private int age;
    private AddressCity addressCity;
    private AddressDistrict addressDistrict;
    private String job;
    private int jobVisibility;
    private int subImgVisibility;
    private int addressVisibility;
    public ProfileResponse() {
    }

    public ProfileResponse(final String nickname, final int gender, final List<PersonalityType> personality
            , final String introduce, final String mainImg, final List<String> subImg
            , final int age, final AddressCity addressCity, final AddressDistrict addressDistrict, final String job
            , final int jobVisibility, final int subImgVisibility, final int addressVisibility) {
        this.nickname = nickname;
        this.gender = gender;
        this.personality = personality;
        this.introduce = introduce;
        this.mainImg = mainImg;
        this.subImg = subImg;
        this.age = age;
        this.addressCity = addressCity;
        this.addressDistrict = addressDistrict;
        this.job = job;
        this.jobVisibility = jobVisibility;
        this.subImgVisibility = subImgVisibility;
        this.addressVisibility = addressVisibility;
    }

    public ProfileResponse(Profile profile) {
        this(profile.getNickname(),
                profile.getGender(),
                profile.getPersonality(),
                profile.getIntroduce(),
                profile.getMainImg(),
                profile.getSubImg(),
                profile.getAge(),
                profile.getAddressCity(),
                profile.getAddressDistrict(),
                profile.getJob(),
                profile.getJobVisible(),
                profile.getSubImgVisible(),
                profile.getAddressVisible());
    }
}
