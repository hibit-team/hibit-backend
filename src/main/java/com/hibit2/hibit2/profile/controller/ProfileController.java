package com.hibit2.hibit2.profile.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hibit2.hibit2.auth.dto.LoginMember;
import com.hibit2.hibit2.auth.support.UserAuthenticationPrincipal;
import com.hibit2.hibit2.profile.dto.request.ProfileRegisterRequest;
import com.hibit2.hibit2.profile.dto.request.ProfileUpdateRequest;
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

    @PostMapping
    @Operation(description = "프로필 등록")
    public ResponseEntity<RegisterProfileResponse> save(@UserAuthenticationPrincipal final LoginMember loginMember,
                                                        @Valid @RequestBody final ProfileRegisterRequest profileRegisterRequest) {
        RegisterProfileResponse profileResponse = profileService.save(loginMember.getId(), profileRegisterRequest);
        return ResponseEntity.created(URI.create("/api/profiles/" + profileResponse.getId())).body(profileResponse);
    }

    @GetMapping("/me/{profileId}")
    @Operation(summary = "/me/profile", description = "프로필 조회")
    public ResponseEntity<UserProfileResponse> findProfileById(@UserAuthenticationPrincipal final LoginMember loginMember,
                                                                @PathVariable final Long profileId) {
        UserProfileResponse response = profileService.findProfileById(profileId);
        return ResponseEntity.ok(response);
    }
}
