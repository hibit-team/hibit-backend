package com.hibit2.hibit2.acceptance;

import com.hibit2.hibit2.auth.dto.response.OAuthUriResponse;
import com.hibit2.hibit2.common.config.TokenConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import static com.hibit2.hibit2.acceptance.fixtures.AuthAcceptanceFixtures.OAuth_인증_URI를_생성한다;
import static com.hibit2.hibit2.acceptance.fixtures.CommonAcceptanceFixtures.상태코드_200이_반환된다;
import static com.hibit2.hibit2.common.AuthFixtures.GOOGLE_PROVIDER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@Import(TokenConfig.class)
@DisplayName("인증 관련 기능")
public class AuthAcceptanceTest extends AcceptanceTest {

    @DisplayName("구글 OAuth 인증 URI를 생성하여 반환한다.")
    @Test
    void 구글_OAuth_인증_URI를_생성하여_반환한다() {
        // given & when
        ExtractableResponse<Response> response = OAuth_인증_URI를_생성한다(GOOGLE_PROVIDER);
        OAuthUriResponse oAuthUriResponse = response.as(OAuthUriResponse.class);

        // then
        assertAll(() -> {
            상태코드_200이_반환된다(response);
            assertThat(oAuthUriResponse.getoAuthUri()).contains("https://");
        });
    }
}