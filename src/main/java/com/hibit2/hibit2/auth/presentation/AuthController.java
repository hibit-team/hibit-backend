package com.hibit2.hibit2.auth.presentation;

import com.hibit2.hibit2.auth.dto.LoginMember;
import com.hibit2.hibit2.auth.dto.OAuthMember;
import com.hibit2.hibit2.auth.dto.request.TokenRenewalRequest;
import com.hibit2.hibit2.auth.dto.response.AccessTokenResponse;
import com.hibit2.hibit2.auth.dto.response.OAuthUriResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;

import com.hibit2.hibit2.auth.dto.request.TokenRequest;
import com.hibit2.hibit2.auth.dto.response.AccessAndRefreshTokenResponse;
import com.hibit2.hibit2.auth.application.OAuthUri;
import com.hibit2.hibit2.auth.application.OAuthClient;
import com.hibit2.hibit2.auth.application.AuthService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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
    @Operation(summary = "/{oauthProvider}/token", description = "액세스 토큰, 리프레시 토큰 발급 받기")
    public ResponseEntity<AccessAndRefreshTokenResponse> generateAccessAndRefreshToken(
            @PathVariable final String oauthProvider, @Valid @RequestBody final TokenRequest tokenRequest,
            HttpServletResponse httpResponse) {
        OAuthMember oAuthMember = oAuthClient.getOAuthMember(tokenRequest.getCode(), tokenRequest.getRedirectUri());
        AccessAndRefreshTokenResponse authResponse = authService.generateAccessAndRefreshToken(oAuthMember);

        // 리프레시 토큰을 쿠키에 설정
        Cookie refreshTokenCookie = new Cookie("refreshToken", authResponse.getRefreshToken());
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setMaxAge(14 * 24 * 60 * 60); // 리프레시 토큰 유효 기간 설정 (14일)
        refreshTokenCookie.setPath("/"); // 쿠키의 유효 경로 설정 (애플리케이션 전체)
        httpResponse.addCookie(refreshTokenCookie);

        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/token/access")
    @Operation(summary = "/token/access", description = "리프레시 토큰으로 새로운 액세스 토큰 발급 받기")
    public ResponseEntity<AccessTokenResponse> generateAccessToken(
            @Valid @RequestBody final TokenRenewalRequest tokenRenewalRequest) {
        AccessTokenResponse response = authService.generateAccessToken(tokenRenewalRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/validate/token")
    @Operation(summary = "/validate/token", description = "웹 페이지 로딩시 유효한 토큰인지 확인")
    public ResponseEntity<Void> validateToken(@AuthenticationPrincipal final LoginMember loginMember) {
        return ResponseEntity.ok().build();
    }
}
