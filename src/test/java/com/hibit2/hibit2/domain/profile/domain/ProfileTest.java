package com.hibit2.hibit2.domain.profile.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProfileTest {

    @DisplayName("프로필을 생성한다.")
    @Test
    void 프로필을_셩상한다() {
        // given
        int matchingNo = 1;
        String email = "fancy.junyongmoon@gmail.com";
        String nickname = "fancy";
        String gender = "남";
        // 히빗 프로필 사진
        String profileImageUrl = "https://github.com/hibit-team/hibit-backend/assets/83820185/108ec6a9-9456-4e6d-b17a-ad0b6fe0d04e";
        String addressCity = "서울";
        String addressDistinct = "용산구";
        String style = "팬시한";
        String personality = "조용한";
        String job = "학생";
        String introduce = "안녕하세요 저는 팬시입니다.";

        // when & then
        Assertions.assertDoesNotThrow(() -> new Profile(matchingNo, email, nickname, gender, profileImageUrl,
                addressCity, addressDistinct, style, personality, job, introduce));
    }

}