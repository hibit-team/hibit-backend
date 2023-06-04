package com.hibit2.hibit2.domain.member.service;

import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hibit2.hibit2.domain.member.domain.Member;
import com.hibit2.hibit2.domain.member.domain.SocialType;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @DisplayName("회원을 저장한다.")
    @Test
    void 회원을_저장한다() {
        // given
        Member member = new Member("fancy.junyongmoon@gmail.com", SocialType.GOOGLE);

        // when
        Member actual = memberService.save(member);

        // then
        Assertions.assertThat(actual).isNotNull();
    }

    @DisplayName("이메일로 회원을 찾는다.")
    @Test
    void 이메일로_회원을_찾는다() {
        // given
        String email = "member@gmail.com";
        Member member = new Member(email, SocialType.GOOGLE);
        Member savedMember = memberService.save(member);

        // when
        Member foundMember = memberService.findByEmail(email);

        // then
        Assertions.assertThat(foundMember.getId()).isEqualTo(savedMember.getId());
    }

    @DisplayName("주어진 이메일로 가입된 회원이 있는지 확인한다.")
    @CsvSource(value = {"registerd@gmail.com,true", "notregistered@naver.com,false"})
    @ParameterizedTest
    void 주어진_이메일로_가입된_회원이_있는지_확인한다(String input, boolean expected) {
        // given
        String email = "registerd@gmail.com";
        Member member = new Member(email, SocialType.GOOGLE);
        memberService.save(member);

        // when
        boolean actual = memberService.existByEmail(input);

        // then
        Assertions.assertThat(actual).isEqualTo(expected);
    }
}