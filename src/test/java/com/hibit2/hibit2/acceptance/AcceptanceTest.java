package com.hibit2.hibit2.acceptance;

import com.hibit2.hibit2.auth.domain.TokenRepository;
import com.hibit2.hibit2.common.DatabaseCleaner;
import com.hibit2.hibit2.common.config.ExternalApiConfig;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ExternalApiConfig.class)
@ActiveProfiles("test")
abstract class AcceptanceTest {

    @LocalServerPort
    private int port;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private TokenRepository tokenRepository;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        databaseCleaner.execute();
        tokenRepository.deleteAll();
    }
}