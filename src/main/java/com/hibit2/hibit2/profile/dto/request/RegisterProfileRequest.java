package com.hibit2.hibit2.profile.dto.request;

import java.util.List;

import javax.validation.constraints.NotBlank;

import com.hibit2.hibit2.member.domain.Member;
import com.hibit2.hibit2.profile.domain.AddressCity;
import com.hibit2.hibit2.profile.domain.AddressDistinct;
import com.hibit2.hibit2.profile.domain.PersonalityType;
import com.hibit2.hibit2.profile.domain.ProfileImage;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RegisterProfileRequest {

    private Member member;

    @NotBlank
    @Schema(description = "닉네임", example = "히빗")
    private String nickname;

    @NotBlank
    @Schema(description = "나이", example = "20")
    private int age;

    @NotBlank
    @Schema(description = "성별", example = "남성")
    private int gender;

    @NotBlank
    @Schema(description = "본인의 성격을 골라주세요.(최대 5개)", example = "[\"유머있는\", \"낙천적인\", \"외향적인\"]")
    private List<PersonalityType> personalityTypeList;

    @NotBlank
    @Schema(description = "메이트에게 자신을 소개해주세요", example = "안녕하세요 저는 20살 남성 히빗이라고 합니다.")
    private String introduce;

    @NotBlank
    @Schema(description = "나의 대표사진", example = "http://hibitbucket")
    private List<ProfileImage> mainImg;

    @Schema(description = "직업 혹은 학교", example = "College student")
    private String job;

    @Schema(description = "시/도", example = "서울")
    private AddressCity addressCity;

    @Schema(description = "시/군/구", example = "서울 용산구")
    private AddressDistinct addressDistinct;

    public RegisterProfileRequest() {
    }

    public RegisterProfileRequest(Member member, String nickname, int age, int gender,
        List<PersonalityType> personalityTypeList, String introduce, List<ProfileImage> mainImg, String job,
        AddressCity addressCity,
        AddressDistinct addressDistinct) {
        this.member = member;
        this.nickname = nickname;
        this.age = age;
        this.gender = gender;
        this.personalityTypeList = personalityTypeList;
        this.introduce = introduce;
        this.mainImg = mainImg;
        this.job = job;
        this.addressCity = addressCity;
        this.addressDistinct = addressDistinct;
    }
}
