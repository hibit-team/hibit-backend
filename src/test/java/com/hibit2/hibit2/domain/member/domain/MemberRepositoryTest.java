package com.hibit2.hibit2.domain.member.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("이메일을 통해 회원을 찾는다.")
    @Test
    void 이메일을_통해_회원을_찾는다() {
        // given
        String email = "fancy.junyongmoon@gmail.com";
        Member member = new Member(email, SocialType.GOOGLE);
        Member savedMember = memberRepository.save(member);

        // when
        Member foundMember = memberRepository.findByEmail(email).get();

        // then
        Assertions.assertThat(savedMember.getId()).isEqualTo(foundMember.getId());
    }

    @DisplayName("중복된 이메일이 존재하는 경우 true를 반환한다.")
    @Test
    void 중복된_이메일이_존재하는_경우_true를_반환한다() {
        // given
        String email = "fancy.junyongmoon@gmail.com";
        Member member = new Member(email, SocialType.GOOGLE);
        memberRepository.save(member);

        // when
        boolean actual = memberRepository.existsByEmail(email);

        // then
        Assertions.assertThat(actual).isTrue();
    }
}