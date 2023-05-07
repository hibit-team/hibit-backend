package com.hibit2.hibit2.auth;


import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Test
    void 구글_로그인을_위한_링크를_생성한다() {
        // given
        String link = authService.generateGoogleLink();

        // when & then
        assertThat(link).isNotEmpty();
    }
}