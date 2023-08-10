package com.hibit2.hibit2.profile.dto.response;

import java.util.List;

import com.hibit2.hibit2.profile.domain.AddressCity;
import com.hibit2.hibit2.profile.domain.AddressDistinct;
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

    // 선택 노출 정보
    private int age;
    private AddressCity addressCity;
    private AddressDistinct addressDistinct;
    private String job;

    // @NoArgsConstructor(access = AccessLevel.PRIVATE) : 아무런 매개변수가 없는 생성자
    // dto - AccessLevel.PRIVATE / entity - AccessLevel.PROTECTED
    public ProfileResponse() {
    }

    // @AllArgsConstructor(access = AccessLevel.PRIVATE) : 해당 클래스 내의 모든 변수값을 가진 생성자를 자동으로 만들어 준다.
    public ProfileResponse(String nickname, int gender, List<PersonalityType> personality, String introduce,
                           String mainImg, int age, AddressCity addressCity, AddressDistinct addressDistinct, String job) {
        this.nickname = nickname;
        this.gender = gender;
        this.personality = personality;
        this.introduce = introduce;
        this.mainImg = mainImg;
        this.age = age;
        this.addressCity = addressCity;
        this.addressDistinct = addressDistinct;
        this.job = job;
    }

    public ProfileResponse(Profile profile) {
        this(profile.getNickname(),
                profile.getGender(),
                profile.getPersonality(),
                profile.getIntroduce(),
                profile.getMainImg(),
                profile.getAge(),
                profile.getAddressCity(),
                profile.getAddressDistinct(),
                profile.getJob());
    }
}
