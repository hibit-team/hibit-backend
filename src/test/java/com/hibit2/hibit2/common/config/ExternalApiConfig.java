package com.hibit2.hibit2.common.config;

import com.hibit2.hibit2.auth.support.OAuthClient;
import com.hibit2.hibit2.auth.support.OAuthUri;
import com.hibit2.hibit2.infrastructure.oauth.client.StubOAuthClient;
import com.hibit2.hibit2.infrastructure.oauth.uri.StubOAuthUri;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class ExternalApiConfig {

    @Bean
    public OAuthClient oAuthClient() {
        return new StubOAuthClient();
    }


    @Bean
    public OAuthUri oAuthUri() {
        return new StubOAuthUri();
    }
}
