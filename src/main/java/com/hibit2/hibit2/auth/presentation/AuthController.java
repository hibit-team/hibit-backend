package com.hibit2.hibit2.auth.presentation;

import com.hibit2.hibit2.auth.dto.LoginMember;
import com.hibit2.hibit2.auth.dto.OAuthMember;
import com.hibit2.hibit2.auth.dto.request.TokenRenewalRequest;
import com.hibit2.hibit2.auth.dto.response.AccessTokenResponse;
import com.hibit2.hibit2.auth.dto.response.OAuthUriResponse;
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
    public ResponseEntity<OAuthUriResponse> generateLink(@PathVariable final String oauthProvider,
                                                         @RequestParam final String redirectUr) {;
        OAuthUriResponse oAuthUriResponse = new OAuthUriResponse(oAuthUri.generate(redirectUr));
        return ResponseEntity.ok(oAuthUriResponse);
    }

    @PostMapping("/{oauthProvider}/token")
    public ResponseEntity<AccessAndRefreshTokenResponse> generateAccessAndRefreshToken(
            @PathVariable final String oauthProvider, @Valid @RequestBody final TokenRequest tokenRequest) {
        OAuthMember oAuthMember = oAuthClient.getOAuthMember(tokenRequest.getCode(), tokenRequest.getRedirectUri());
        AccessAndRefreshTokenResponse response = authService.generateAccessAndRefreshToken(oAuthMember);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/token/access")
    public ResponseEntity<AccessTokenResponse> generateAccessToken(
            @Valid @RequestBody final TokenRenewalRequest tokenRenewalRequest) {
        AccessTokenResponse response = authService.generateAccessToken(tokenRenewalRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/validate/token")
    public ResponseEntity<Void> validateToken(@AuthenticationPrincipal final LoginMember loginMember) {
        return ResponseEntity.ok().build();
    }
}
