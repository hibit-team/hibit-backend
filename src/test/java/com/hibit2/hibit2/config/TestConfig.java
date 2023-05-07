package com.hibit2.hibit2.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import com.hibit2.hibit2.infrastructure.client.OAuthClient;
import com.hibit2.hibit2.infrastructure.oauth.client.StubOAuthClient;

@TestConfiguration
public class TestConfig {

    // SpringBootTest 환경에서 OAuthClient 실제 객체 대신 테스트 더블을 빈으로 등록하기 위한 코드
    @Bean
    public OAuthClient oAuthClient() {
        return new StubOAuthClient();
    }
}