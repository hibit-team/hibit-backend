package com.hibit2.hibit2.profile.dto.request;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.hibit2.hibit2.profile.domain.AddressCity;
import com.hibit2.hibit2.profile.domain.AddressDistrict;
import com.hibit2.hibit2.profile.domain.PersonalityType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ProfileUpdateRequest {

    @NotBlank(message = "공백일 수 없습니다.")
    @Schema(description = "닉네임", example = "히빗2")
    private String nickname;

    @NotNull(message = "공백일 수 없습니다.")
    @Schema(description = "나이", example = "26")
    private int age;

    @NotNull(message = "공백일 수 없습니다.")
    @Schema(description = "성별", example = "1")
    private int gender;

    @NotBlank(message = "공백일 수 없습니다.")
    @Schema(description = "본인의 성격을 골라주세요.(최대 5개)", example = "[\"감성적인\", \"귀여운\", \"열정적인\", \"친절한\", \"섬세한\"]")
    private List<PersonalityType> personality;

    @NotBlank(message = "공백일 수 없습니다.")
    @Schema(description = "메이트에게 자신을 소개해주세요", example = "안녕하세요 저는 26살 여성 히빗2라고 합니다.")
    private String introduce;

    @NotBlank(message = "공백일 수 없습니다.")
    @Schema(description = "나의 대표사진", example = "http://hibitbucket")
    private String mainImg;

    @Schema(description = "나머지 이미지 url 리스트", example = "[\"http://hibitbucket1\", \"http://hibitbucket2\"]")
    private List<String> subImg;

    @Schema(description = "직업 혹은 학교", example = "디자이너")
    private String job;

    @Schema(description = "시/도", example = "인천광역시")
    private AddressCity addressCity;

    @Schema(description = "시/군/구", example = "연수구")
    private AddressDistrict addressDistrict;

    @Schema(description = "직업 공개 여부", example = "0")
    private int jobVisibility;

    @Schema(description = "서브 이미지 공개 여부", example = "0")
    private int subImgVisibility;

    @Schema(description = "주소 공개 여부", example = "0")
    private int addressVisibility;

    public ProfileUpdateRequest() {
    }

    public ProfileUpdateRequest(final String nickname, final int age, final int gender, final List<PersonalityType> personality,
                                final String introduce,
                                final String mainImg, final List<String> subImg, final String job, final AddressCity addressCity, final AddressDistrict addressDistrict,
                                final int jobVisibility, final int subImgVisibility, final int addressVisibility) {
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
        this.jobVisibility = jobVisibility;
        this.subImgVisibility = subImgVisibility;
        this.addressVisibility = addressVisibility;
    }
}
