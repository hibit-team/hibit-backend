package com.hibit2.hibit2.common.annotation;


import com.hibit2.hibit2.auth.dto.OAuthMember;
import com.hibit2.hibit2.auth.dto.response.AccessAndRefreshTokenResponse;
import com.hibit2.hibit2.auth.repository.TokenRepository;
import com.hibit2.hibit2.auth.service.AuthService;
import com.hibit2.hibit2.common.DatabaseCleaner;
import com.hibit2.hibit2.common.builder.BuilderSupporter;
import com.hibit2.hibit2.common.builder.GivenBuilder;
import com.hibit2.hibit2.common.config.ExternalApiConfig;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static com.hibit2.hibit2.common.Constants.팬시_이메일;


@SpringBootTest(classes = ExternalApiConfig.class)
@ActiveProfiles("test")
public abstract class ServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private BuilderSupporter builderSupporter;

    @BeforeEach
    void setUp() {
        databaseCleaner.execute();
        tokenRepository.deleteAll();
    }

    protected Long toMemberId(final OAuthMember oAuthMember) {
        AccessAndRefreshTokenResponse response = authService.generateAccessAndRefreshToken(oAuthMember);
        return authService.extractMemberId(response.getRefreshToken());
    }

    protected GivenBuilder 팬시() {
        GivenBuilder 팬시 = new GivenBuilder(builderSupporter);
        팬시.회원_가입을_한다(팬시_이메일);
        return 팬시;
    }
}
