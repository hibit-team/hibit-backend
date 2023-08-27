package com.hibit2.hibit2.profile.dto.response;

import com.hibit2.hibit2.profile.domain.AddressCity;
import com.hibit2.hibit2.profile.domain.AddressDistrict;
import com.hibit2.hibit2.profile.domain.PersonalityType;
import com.hibit2.hibit2.profile.domain.Profile;
import lombok.*;

import java.util.List;

@Getter
public class ProfileOtherResponse {
    // 필수 노출 정보
    private String nickname;
    private int gender;
    private List<PersonalityType> personality;
    private String introduce;
    private String mainImg;
    private String subImg;

    // 선택 노출 정보
    private int age;
    private AddressCity addressCity;
    private AddressDistrict addressDistrict;
    private String job;

    public ProfileOtherResponse() {
    }

    public ProfileOtherResponse(Profile profile) {
        this(profile.getNickname(),
                profile.getGender(),
                profile.getPersonality(),
                profile.getIntroduce(),
                profile.getMainImg(),
                profile.getSubImg().toString(),
                profile.getAge(),
                profile.getAddressCity(),
                profile.getAddressDistrict(),
                profile.getJob());
    }

    public ProfileOtherResponse(String nickname, int gender, List<PersonalityType> personality, String introduce, String mainImg, String subImg, int age, AddressCity addressCity, AddressDistrict addressDistrict, String job) {
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
    }
}
