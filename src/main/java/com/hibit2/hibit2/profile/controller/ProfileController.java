package com.hibit2.hibit2.profile.controller;

import java.net.URI;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hibit2.hibit2.auth.presentation.AuthenticationPrincipal;
import com.hibit2.hibit2.profile.dto.response.ProfileOtherResponse;
import com.hibit2.hibit2.profile.dto.response.ProfilesResponse;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hibit2.hibit2.auth.dto.LoginMember;
import com.hibit2.hibit2.profile.dto.request.ProfileRegisterRequest;
import com.hibit2.hibit2.profile.dto.request.ProfileUpdateRequest;
import com.hibit2.hibit2.profile.dto.response.ProfileRegisterResponse;
import com.hibit2.hibit2.profile.dto.response.ProfileResponse;
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
    public ResponseEntity<Void> save(@Parameter(hidden = true) @AuthenticationPrincipal final LoginMember loginMember,
                                     @Valid @RequestBody final ProfileRegisterRequest profileRegisterRequest) {
        ProfileRegisterResponse response = profileService.save(loginMember.getId(), profileRegisterRequest);
        return ResponseEntity.created(URI.create("/api/profiles/" + response.getId())).build();
    }

    @GetMapping
    @Operation(description = "프로필 전체 조회")
    public ResponseEntity<ProfilesResponse> findProfiles() {
        ProfilesResponse response = profileService.findProfilesByIdAndMemberId();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me/{profileId}")
    @Operation(summary = "/me/profile", description = "본인 프로필 조회")
    public ResponseEntity<ProfileResponse> findProfileById(@AuthenticationPrincipal final LoginMember loginMember,
                                                           @PathVariable final Long profileId) {
        ProfileResponse response = profileService.findProfileByIdAndMemberId(loginMember, profileId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/other/{profileId}")
    @Operation(summary = "other/profile", description = "타인 프로필 조회")
    public ResponseEntity<ProfileOtherResponse> findProfileByOtherId(@PathVariable Long profileId) {
        ProfileOtherResponse response = profileService.findOtherProfile(profileId);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/me/{profileId}")
    @Operation(summary = "/me/profile", description = "프로필 수정")
    public ResponseEntity<Void> update(@Parameter(hidden = true) @AuthenticationPrincipal final LoginMember loginMember,
                                       @PathVariable final Long profileId,
                                       @Valid @RequestBody final ProfileUpdateRequest profileUpdateRequest) {
        profileService.update(loginMember.getId(), profileId, profileUpdateRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}