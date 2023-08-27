package com.hibit2.hibit2.common.config;

import com.hibit2.hibit2.auth.application.TokenProvider;
import com.hibit2.hibit2.auth.application.StubTokenProvider;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import static com.hibit2.hibit2.common.AuthFixtures.더미_시크릿_키;

@TestConfiguration
public class TokenConfig {

    @Bean
    public TokenProvider tokenProvider() {
        return new StubTokenProvider(더미_시크릿_키);
    }
}
