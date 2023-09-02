package com.hibit2.hibit2.auth.presentation;

import com.hibit2.hibit2.auth.dto.LoginMember;
import com.hibit2.hibit2.auth.dto.OAuthMember;
import com.hibit2.hibit2.auth.dto.request.TokenRenewalRequest;
import com.hibit2.hibit2.auth.dto.response.AccessTokenResponse;
import com.hibit2.hibit2.auth.dto.response.OAuthUriResponse;
import com.hibit2.hibit2.global.token.Login;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import com.hibit2.hibit2.auth.dto.request.TokenRequest;
import com.hibit2.hibit2.auth.application.OAuthUri;
import com.hibit2.hibit2.auth.application.OAuthClient;
import com.hibit2.hibit2.auth.application.AuthService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.CookieValue;

import javax.validation.Valid;

@Tag(name = "auth", description = "인증/인가")
@RequestMapping("/api/auth")
@RestController
public class AuthController {

    private final OAuthUri oAuthUri;
    private final OAuthClient oAuthClient;
    private final AuthService authService;

    public AuthController(final OAuthUri oAuthUri, final OAuthClient oAuthClient, final AuthService authService) {
        this.oAuthUri = oAuthUri;
        this.oAuthClient = oAuthClient;
        this.authService = authService;
    }

    @GetMapping("/{oauthProvider}/oauth-uri")
    @Operation(summary = "/{oauthProvider}/oauth-uri", description = "로그인 요청")
    public ResponseEntity<OAuthUriResponse> generateLink(@PathVariable final String oauthProvider,
                                                         @RequestParam final String redirectUri) {
        OAuthUriResponse oAuthUriResponse = new OAuthUriResponse(oAuthUri.generate(redirectUri));
        return ResponseEntity.ok(oAuthUriResponse);
    }
    @PostMapping("/{oauthProvider}/token")
    @Operation(summary = "/{oauthProvider}/token", description = "액세스 토큰은 Body로 발급, 리프레시 토큰은 Set-Cookie로 발급 받기")
    public ResponseEntity<AccessTokenResponse> generateAccessAndRefreshToken(
            @PathVariable final String oauthProvider, @Valid @RequestBody final TokenRequest tokenRequest) {
        OAuthMember oAuthMember = oAuthClient.getOAuthMember(tokenRequest.getCode(), tokenRequest.getRedirectUri());
        AccessTokenResponse authResponse = authService.generateAccessAndRefreshToken(oAuthMember);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/token/access")
    @Operation(summary = "/token/access", description = "리프레시 토큰으로 새로운 액세스 토큰 발급 받기")
    public ResponseEntity<AccessTokenResponse> generateAccessToken(
           @CookieValue("refreshToken") String refreshToken) {
        TokenRenewalRequest tokenRenewalRequest = new TokenRenewalRequest(refreshToken);
        AccessTokenResponse response = authService.generateAccessToken(tokenRenewalRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/validate/token")
    @Operation(summary = "/validate/token", description = "웹 페이지 로딩시 유효한 토큰인지 확인")
    public ResponseEntity<Void> validateToken(@AuthenticationPrincipal final LoginMember loginMember) {
        return ResponseEntity.ok().build();
    }
    @GetMapping("/logout")
    @Operation(summary = "/logout", description = "로그아웃 시, 서버에서 accessToken과 refreshToken값을 만료시킨다.")
    public ResponseEntity<Void> logout(@Login LoginMember loginMember) {
        authService.deleteToken(loginMember.getId());
        return ResponseEntity.noContent().build();
    }
}
