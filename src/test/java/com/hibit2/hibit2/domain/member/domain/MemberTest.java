package com.hibit2.hibit2.domain.member.domain;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.hibit2.hibit2.domain.member.domain.Member;
import com.hibit2.hibit2.domain.member.domain.Role;
import com.hibit2.hibit2.domain.member.exception.InvalidMemberException;

class MemberTest {
    @DisplayName("회원을 생성한다.")
    @Test
    void 회원을_생성한다() {
        //given
        String email = "fancy.junyongmoon@gmail.com";
        String gender = "남";
        int age = 28;
        Role role = Role.USER;

        // when & then
        assertDoesNotThrow(() -> new Member(email, gender, age, role));
    }


    @DisplayName("회원의 email 형식이 맞지 않으면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"fancy.junyongmoon@", "fancy.junyongmoon@gmail", "fancy.junyongmoon@gmail.com", "fancy.junyongmoon"})
    void 회원의_email_형식이_맞지_않으면_예외가_발생한다(final String email) {
        // given
        String gender = "남";
        int age = 28;
        Role role = Role.USER;

        // when & then
        assertThatThrownBy(() -> new Member(email, gender, age, role))
            .isInstanceOf(InvalidMemberException.class)
            .hasMessage("이메일 형식이 올바르지 않습니다.");
    }
}