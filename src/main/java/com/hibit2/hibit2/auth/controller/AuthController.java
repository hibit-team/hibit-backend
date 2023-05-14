package com.hibit2.hibit2.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hibit2.hibit2.auth.dto.TokenRequest;
import com.hibit2.hibit2.auth.dto.TokenResponse;
import com.hibit2.hibit2.auth.service.AuthService;
import com.hibit2.hibit2.global.dto.CommonResponse;

@RequestMapping("/api/auth")
@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(final AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/{oauthProvider}/link")
    public ResponseEntity<CommonResponse<String>>
    generateLink(@PathVariable final String oauthProvider) {
        return ResponseEntity.ok(new CommonResponse<>
            (authService.generateGoogleLink()));
    }

    @PostMapping("/{oauthProvider}/token")
    public ResponseEntity<CommonResponse<TokenResponse>>
    generateToken(@PathVariable final String oauthProvider,
        @RequestBody final TokenRequest tokenRequest) {
        TokenResponse tokenResponse = authService.generateTokenWithCode(tokenRequest.getCode());
        return ResponseEntity.ok(new CommonResponse<>(tokenResponse));
    }
}
