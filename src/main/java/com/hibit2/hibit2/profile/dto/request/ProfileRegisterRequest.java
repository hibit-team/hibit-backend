package com.hibit2.hibit2.profile.dto.request;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.hibit2.hibit2.member.domain.Member;
import com.hibit2.hibit2.profile.domain.AddressCity;
import com.hibit2.hibit2.profile.domain.AddressDistrict;
import com.hibit2.hibit2.profile.domain.PersonalityType;
import com.hibit2.hibit2.profile.domain.Profile;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ProfileRegisterRequest {

    @NotBlank(message = "공백일 수 없습니다.")
    @Schema(description = "닉네임", example = "히빗")
    private String nickname;

    @NotNull(message = "공백일 수 없습니다.")
    @Schema(description = "나이", example = "25")
    private int age;

    @NotNull(message = "공백일 수 없습니다.")
    @Schema(description = "성별", example = "0")
    private int gender;

    @NotBlank(message = "공백일 수 없습니다.")
    @Schema(description = "본인의 성격을 골라주세요.(최대 5개)", example = "[\"창의적인\", \"책임감이 강한\", \"외향적인\", \"개성있는\", \"계획적인\"]")
    private List<PersonalityType> personality;

    @NotBlank(message = "공백일 수 없습니다.")
    @Schema(description = "메이트에게 자신을 소개해주세요", example = "안녕하세요 저는 25살 남성 히빗이라고 합니다.")
    private String introduce;

    @NotBlank(message = "공백일 수 없습니다.")
    @Schema(description = "나의 대표사진", example = "http://hibitbucket")
    private String mainImg;

    @Schema(description = "나머지 이미지 url 리스트", example = "[\"http://hibitbucket1\", \"http://hibitbucket2\"]" )
    private List<String > subImg;

    @Schema(description = "직업 혹은 학교", example = "대학생")
    private String job;

    @Schema(description = "시/도", example = "서울특별시")
    private AddressCity addressCity;

    @Schema(description = "시/군/구", example = "용산구")
    private AddressDistrict addressDistrict;

    public ProfileRegisterRequest() {
    }

    public ProfileRegisterRequest(String nickname, int age, int gender, List<PersonalityType> personality,
                                  String introduce, String mainImg, List<String> subImg, String job, AddressCity addressCity,
                                  AddressDistrict addressDistrict) {
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
    public Profile toEntity(final Member member) {
        return new Profile(member, nickname, age, gender, personality, introduce, mainImg, subImg, job, addressCity, addressDistrict);
    }
}
