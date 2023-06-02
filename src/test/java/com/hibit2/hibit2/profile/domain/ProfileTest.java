package com.hibit2.hibit2.profile.domain;

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
        int gender = 1;
        PersonalityType personalityType = PersonalityType.TYPE_1;
        String introduce = "Hi, I'm fancy.";
        String mainImg = "profile.jpg";
        String job = "Backend";
        AddressCity addressCity = AddressCity.NEW_YORK;
        AddressDistinct addressDistinct = AddressDistinct.MANHATTAN;
        int ban = 1;

        // when & then
        Assertions.assertDoesNotThrow(() -> new Profile(matchingNo, email, nickname, gender,
                personalityType, introduce, mainImg, job, addressCity, addressDistinct, ban));
    }

}