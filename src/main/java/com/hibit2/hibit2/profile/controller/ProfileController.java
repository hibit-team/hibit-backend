package com.hibit2.hibit2.profile.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hibit2.hibit2.auth.support.UserAuthenticationPrincipal;
import com.hibit2.hibit2.profile.dto.request.RegisterProfileRequest;
import com.hibit2.hibit2.profile.dto.response.RegisterProfileResponse;
import com.hibit2.hibit2.profile.dto.response.UserProfileResponse;
import com.hibit2.hibit2.profile.service.ProfileService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    // 프로필 등록
    @PostMapping
    @Operation(summary = "/profiles/{profile.getId()}", description = "프로필 등록")
    public ResponseEntity<Void> registerProfile(@Valid @RequestBody RegisterProfileRequest registerProfileRequest) {

        RegisterProfileResponse profile = profileService.registerProfile(registerProfileRequest);

        return ResponseEntity.created(URI.create("/profiles/" + profile.getId())).build();
    }

    // 프로필 조회
    @GetMapping
    @Operation(summary = "/me/profile", description = "프로필 조회")
    public ResponseEntity<UserProfileResponse> getProfile(@UserAuthenticationPrincipal int profileId) {
        UserProfileResponse response = profileService.getProfile(profileId);
        return ResponseEntity.ok(response);
    }
}
