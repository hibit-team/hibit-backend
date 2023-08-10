package com.hibit2.hibit2.profile.dto.response;

import com.hibit2.hibit2.profile.domain.PersonalityType;
import com.hibit2.hibit2.profile.domain.Profile;
import lombok.*;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ProfileOtherResponse {
    private Long id;
    // 필수 노출 정보
    private String nickname;
    private int gender;
    private List<PersonalityType> personality;
    private String introduce;
    private String mainImg;

    public static ProfileOtherResponse from(Profile profile) {
        return ProfileOtherResponse.builder()
                .id(profile.getId())
                .nickname(profile.getNickname())
                .gender(profile.getGender())
                .personality(profile.getPersonality())
                .introduce(profile.getIntroduce())
                .mainImg(profile.getMainImg())
                .build();
    }
}
