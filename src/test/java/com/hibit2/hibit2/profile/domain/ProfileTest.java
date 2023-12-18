package com.hibit2.hibit2.profile.domain;

import com.hibit2.hibit2.member.domain.Member;
import com.hibit2.hibit2.profile.exception.InvalidNicknameException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.hibit2.hibit2.common.fixtures.MemberFixtures.팬시;
import static com.hibit2.hibit2.common.fixtures.ProfileFixtures.팬시_프로필;
import static com.hibit2.hibit2.common.fixtures.ProfileFixtures.팬시_나이;
import static com.hibit2.hibit2.common.fixtures.ProfileFixtures.팬시_성별;
import static com.hibit2.hibit2.common.fixtures.ProfileFixtures.팬시_성격들;
import static com.hibit2.hibit2.common.fixtures.ProfileFixtures.팬시_자기소개;
import static com.hibit2.hibit2.common.fixtures.ProfileFixtures.팬시_메인_이미지;
import static com.hibit2.hibit2.common.fixtures.ProfileFixtures.팬시_서브_이미지;
import static com.hibit2.hibit2.common.fixtures.ProfileFixtures.팬시_직업;
import static com.hibit2.hibit2.common.fixtures.ProfileFixtures.팬시_도시;
import static com.hibit2.hibit2.common.fixtures.ProfileFixtures.팬시_도시_구;
import static com.hibit2.hibit2.common.fixtures.ProfileFixtures.팬시_직업_선택여부;
import static com.hibit2.hibit2.common.fixtures.ProfileFixtures.팬시_서브_이미지_선택여부;
import static com.hibit2.hibit2.common.fixtures.ProfileFixtures.팬시_주소_선택여부;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ProfileTest {

    @DisplayName("프로필을 생성한다.")
    @Test
    void 프로필을_생성한다() {
        // given & when & then
        assertDoesNotThrow(()-> 팬시_프로필());
    }
    @DisplayName("닉네임이 비거나 20글자가 넘으면 예외를 던진다.")
    @ParameterizedTest
    @ValueSource(strings = {"", "일이삼사오육칠팔구십" + "일이삼사오육칠팔구십일", "하나둘셋넷다섯여섯일곱여덟아홉열" +"하나둘셋넷다섯여섯일곱여덟아홉열" + "하나둘셋넷다섯여섯일곱여덟아홉열"})
    void 닉네임이_비거나_20글자가_넘으면_예외를_던진다(final String nickname) {
        // given & when & then
        Member member = 팬시();
        assertThatThrownBy(() -> new Profile(member, nickname, 팬시_나이, 팬시_성별, 팬시_성격들, 팬시_자기소개
                 , 팬시_메인_이미지, 팬시_서브_이미지, 팬시_직업, 팬시_도시, 팬시_도시_구, 팬시_직업_선택여부, 팬시_서브_이미지_선택여부,팬시_주소_선택여부 ))
                .isInstanceOf(InvalidNicknameException.class);
     }

    @DisplayName("닉네임이 비거나 20글자가 넘지 않으면 성공한다.")
    @ParameterizedTest
    @ValueSource(strings = {"일", "일이삼사오육칠팔구십", "하나둘셋넷다섯여섯일곱여덟아홉열"})
    void 닉네임이_한글자_이상_20글자가_넘지_않으면_성공한다(final String nickname) {
        // given & when & then
        Member member = 팬시();
        assertDoesNotThrow(() -> new Profile(member, nickname, 팬시_나이, 팬시_성별, 팬시_성격들, 팬시_자기소개
                , 팬시_메인_이미지, 팬시_서브_이미지, 팬시_직업, 팬시_도시, 팬시_도시_구, 팬시_직업_선택여부, 팬시_서브_이미지_선택여부,팬시_주소_선택여부 ));
    }

    @DisplayName("프로필에서 닉네임을 변경한다.")
    @Test
    void 프로필에서_닉네임을_변경한다() {
        // given
        Member member = 팬시();
        String 밴시_이름 = "밴시";

        // when
        member.updateNickname(밴시_이름);

        // then
        assertThat(member.getNickname()).isEqualTo(밴시_이름);
     }
}