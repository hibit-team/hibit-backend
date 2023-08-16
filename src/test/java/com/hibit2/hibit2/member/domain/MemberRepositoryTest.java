package com.hibit2.hibit2.member.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.hibit2.hibit2.member.repository.MemberRepository;

import static com.hibit2.hibit2.common.fixtures.MemberFixtures.팬시;
import static com.hibit2.hibit2.common.fixtures.MemberFixtures.팬시_이메일;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("이메일을 통해 회원을 찾는다.")
    @Test
    void 이메일을_통해_회원을_찾는다() {
        // given
        Member 팬시 = memberRepository.save(팬시());

        // when
        Member actual = memberRepository.getByEmail(팬시_이메일);

        // then
        assertThat(actual.getId()).isEqualTo(팬시.getId());
    }

    @DisplayName("중복된 이메일이 존재하는 경우 true를 반환한다.")
    @Test
    void 중복된_이메일이_존재하는_경우_true를_반환한다() {
        // given
        memberRepository.save(팬시());

        // when
        boolean actual = memberRepository.existsByEmail(팬시_이메일);

        // then
        Assertions.assertThat(actual).isTrue();
    }
}