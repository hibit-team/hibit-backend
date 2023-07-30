package com.hibit2.hibit2.member.service;

import com.hibit2.hibit2.common.annotation.ServiceTest;
import com.hibit2.hibit2.common.builder.GivenBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hibit2.hibit2.member.domain.Member;
import com.hibit2.hibit2.member.domain.SocialType;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MemberServiceTest extends ServiceTest {

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

    @DisplayName("회원을 조회한다.")
    @Test
    void 회원을_조회한다() {
        // given
        GivenBuilder 팬시 = 팬시();

        // when & then
        assertThat(memberService.findById(팬시.회원().getId()).getId())
                .isEqualTo(팬시.회원().getId());
    }
}