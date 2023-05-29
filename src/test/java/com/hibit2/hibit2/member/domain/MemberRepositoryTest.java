package com.hibit2.hibit2.member.domain;

import com.hibit2.hibit2.member.domain.Member;
import com.hibit2.hibit2.member.domain.MemberRepository;
import com.hibit2.hibit2.member.domain.Role;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("중복된 이메일이 존재하는 경우 true를 반환한다.")
    @Test
    void 중복된_이메일이_존재하는_경우_true를_반환한다() {
        // given
        String email = "fancy.junyongmoon@gmail.com";
        String gender = "남";
        int age = 28;
        Member member = new Member(email, gender, age, Role.USER);
        memberRepository.save(member);

        // when
        boolean actual = memberRepository.existsByEmail(email);

        // then
        Assertions.assertThat(actual).isTrue();
    }
}