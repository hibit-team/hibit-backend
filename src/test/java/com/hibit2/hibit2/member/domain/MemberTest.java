package com.hibit2.hibit2.member.domain;

import static com.hibit2.hibit2.common.fixtures.MemberFixtures.팬시_이메일;
import static com.hibit2.hibit2.common.fixtures.MemberFixtures.팬시_이름;
import static com.hibit2.hibit2.common.fixtures.MemberFixtures.팬시_프로필;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.hibit2.hibit2.member.exception.InvalidMemberException;

class MemberTest {
    @DisplayName("회원을 생성한다.")
    @Test
    void 회원을_생성한다() {

        // given & when & then
        assertDoesNotThrow(() -> new Member(팬시_이메일, 팬시_이름, 팬시_프로필, SocialType.GOOGLE));
    }


    @DisplayName("회원의 email 형식이 맞지 않으면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"fancy.junyongmoon@", "fancy.junyongmoon@amail", "fancy.junyongmoon"})
    void 회원의_email_형식이_맞지_않으면_예외가_발생한다(final String email) {

        // given & when & then
        assertThatThrownBy(() -> new Member(email, 팬시_이름, 팬시_프로필, SocialType.GOOGLE))
            .isInstanceOf(InvalidMemberException.class);
    }
}