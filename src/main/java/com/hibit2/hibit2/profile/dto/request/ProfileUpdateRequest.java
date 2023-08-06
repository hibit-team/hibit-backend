package com.hibit2.hibit2.profile.dto.request;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.hibit2.hibit2.profile.domain.AddressCity;
import com.hibit2.hibit2.profile.domain.AddressDistinct;
import com.hibit2.hibit2.profile.domain.PersonalityType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ProfileUpdateRequest {

    @NotBlank(message = "공백일 수 없습니다.")
    @Schema(description = "닉네임", example = "히빗2")
    private String nickname;

    @NotNull(message = "공백일 수 없습니다.")
    @Schema(description = "나이", example = "30")
    private int age;

    @NotNull(message = "공백일 수 없습니다.")
    @Schema(description = "성별", example = "1")
    private int gender;

    @NotBlank(message = "공백일 수 없습니다.")
    @Schema(description = "본인의 성격을 골라주세요.(최대 5개)", example = "[\"TYPE_1\", \"TYPE_2\", \"TYPE_3\"]")
    private List<PersonalityType> personality;

    @NotBlank(message = "공백일 수 없습니다.")
    @Schema(description = "메이트에게 자신을 소개해주세요", example = "안녕하세요 저는 30살 여성 히빗2라고 합니다.")
    private String introduce;

    @NotBlank(message = "공백일 수 없습니다.")
    @Schema(description = "나의 대표사진", example = "http://hibitbucket")
    private String mainImg;

    @Schema(description = "나머지 이미지 url 리스트", example = "[\"http://hibitbucket1\", \"http://hibitbucket2\"]" )
    private List<String > subImg;

    @Schema(description = "직업 혹은 학교", example = "College student")
    private String job;

    @Schema(description = "시/도", example = "SEOUL")
    private AddressCity addressCity;

    @Schema(description = "시/군/구", example = "SEOUL_JONGRO")
    private AddressDistinct addressDistinct;

    public ProfileUpdateRequest() {
    }

    public ProfileUpdateRequest(String nickname, int age, int gender, List<PersonalityType> personality,
        String introduce,
        String mainImg, List<String> subImg, String job, AddressCity addressCity, AddressDistinct addressDistinct) {
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
}
