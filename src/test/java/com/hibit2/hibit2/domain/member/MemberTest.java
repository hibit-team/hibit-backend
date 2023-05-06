package com.hibit2.hibit2.domain.member;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.hibit2.hibit2.domain.member.exception.InvalidMemberException;

class MemberTest {
    @DisplayName("회원을 생성한다.")
    @Test
    void 회원을_생성한다() {
        //given
        String email = "fancy.junyongmoon@gmail.com";
        String name = "Moon Junyong";
        String profileImageUrl = "https://avatars.githubusercontent.com/u/83820185?v=4";
        Role role = Role.USER;


        // when & then
        assertDoesNotThrow(() -> new Member(email, name, profileImageUrl, role));
    }


    @DisplayName("회원의 email 형식이 맞지 않으면 예외를 던진다.")
    @ParameterizedTest
    @ValueSource(strings = {"fancy.junyongmoon@", "fancy.junyongmoon@gmail", "fancy.junyongmoon@gmail.com", "fancy.junyongmoon"})
    void 회원의_email_형식이_맞지_않으면_예외를_던진다(final String email) {
        // given
        String name = "Moon Junyong";
        String profileImageUrl = "https://avatars.githubusercontent.com/u/83820185?v=4";
        Role role = Role.USER;

        // when & then
        assertThatThrownBy(() -> new Member(email, name, profileImageUrl, role))
            .isInstanceOf(InvalidMemberException.class)
            .hasMessage("이메일 형식이 올바르지 않습니다.");
    }
}