package com.hibit2.hibit2.config;

import com.hibit2.hibit2.auth.service.TokenProvider;
import com.hibit2.hibit2.auth.support.StubTokenProvider;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;


import static com.hibit2.hibit2.common.AuthFixtures.더미_시크릿_키;

@TestConfiguration
public class TestConfig {

    // SpringBootTest 환경에서 OAuthClient 실제 객체 대신 테스트 더블을 빈으로 등록하기 위한 코드
    @Bean
    public TokenProvider tokenProvider() {
        return new StubTokenProvider(더미_시크릿_키);
    }
}