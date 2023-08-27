package com.hibit2.hibit2.member.service;

import com.hibit2.hibit2.common.annotation.ServiceTest;
import com.hibit2.hibit2.common.builder.GivenBuilder;
import com.hibit2.hibit2.member.repository.MemberRepository;
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