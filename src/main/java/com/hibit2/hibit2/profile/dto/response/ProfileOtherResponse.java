package com.hibit2.hibit2.profile.dto.response;

import com.hibit2.hibit2.profile.domain.AddressCity;
import com.hibit2.hibit2.profile.domain.AddressDistrict;
import com.hibit2.hibit2.profile.domain.PersonalityType;
import com.hibit2.hibit2.profile.domain.Profile;
import lombok.*;

import java.util.List;

@Getter
@Setter
public class ProfileOtherResponse {
    // 필수 노출 정보
    private String nickname;
    private int gender;
    private List<PersonalityType> personality;
    private String introduce;
    private String mainImg;
    private int age;

    // 선택 노출 정보(공개/비공개)
    private String subImg;
    private AddressCity addressCity;
    private AddressDistrict addressDistrict;
    private String job;

    private boolean jobVisibility;
    private boolean subImgVisibility;
    private boolean addressVisibility;

    public ProfileOtherResponse() {
    }

    public ProfileOtherResponse(Profile profile) {
        this(profile.getNickname(),
                profile.getGender(),
                profile.getPersonality(),
                profile.getIntroduce(),
                profile.getMainImg(),
                null,  // subImg 초기값은 null로 설정
                profile.getAge(),
                null,  // addressCity 초기값은 null로 설정
                null,  // addressDistrict 초기값은 null로 설정
                null,  // job 초기값은 null로 설정
                profile.isJobVisible(),
                profile.isSubImgVisible(),
                profile.isAddressVisible());
        // 공개 여부에 따라 값 설정
        if (profile.isSubImgVisible()) {
            this.subImg = profile.getSubImg().toString();
        }
        if (profile.isAddressVisible()) {
            this.addressCity = profile.getAddressCity();
            this.addressDistrict = profile.getAddressDistrict();
        }
        if (profile.isJobVisible()) {
            this.job = profile.getJob();
        }
    }

    public ProfileOtherResponse(String nickname, int gender, List<PersonalityType> personality, String introduce, String mainImg, String subImg,
                                int age, AddressCity addressCity, AddressDistrict addressDistrict, String job,
                                boolean jobVisibility, boolean subImgVisibility, boolean addressVisibility) {
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
}
