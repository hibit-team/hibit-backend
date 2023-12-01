package com.hibit2.hibit2.profile.controller;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import com.hibit2.hibit2.auth.presentation.AuthenticationPrincipal;
import com.hibit2.hibit2.profile.domain.PersonalityType;
import com.hibit2.hibit2.profile.dto.response.*;
import com.hibit2.hibit2.profile.exception.NicknameAlreadyTakenException;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hibit2.hibit2.auth.dto.LoginMember;
import com.hibit2.hibit2.profile.dto.request.ProfileRegisterRequest;
import com.hibit2.hibit2.profile.dto.request.ProfileUpdateRequest;
import com.hibit2.hibit2.profile.service.ProfileService;

import io.swagger.v3.oas.annotations.Operation;

@Tag(name = "profile", description = "프로필")
@RestController
@RequestMapping("/api/profiles")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping
    @Operation(description = "본인 프로필을 등록한다.")
    public ResponseEntity<ProfileRegisterResponse> saveMyProfile(@Parameter(hidden = true) @AuthenticationPrincipal final LoginMember loginMember,
                                                        @Valid @RequestBody final ProfileRegisterRequest profileRegisterRequest) {
        ProfileRegisterResponse response = profileService.saveMyProfile(loginMember.getId(), profileRegisterRequest);
        return ResponseEntity.created(URI.create("/api/profiles/" + response.getId())).body(response);
    }

    @GetMapping("/personalities")
    @Operation(summary = "/personalities", description = "사용자에게 선택할 수 있는 성격 목록을 반환합니다.")
    public ResponseEntity<List<PersonalityType>> getAvailablePersonalities() {
        List<PersonalityType> personalities = Arrays.asList(PersonalityType.values());
        return ResponseEntity.ok(personalities);
    }

    @GetMapping
    @Operation(description = "등록된 프로필 전체를 조회한다.")
    public ResponseEntity<ProfilesResponse> findProfiles() {
        ProfilesResponse response = profileService.findProfilesByIdAndMemberId();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    @Operation(summary = "/me", description = "본인 프로필을 조회한다.")
    public ResponseEntity<ProfileResponse> findProfileByMemberId(@AuthenticationPrincipal final LoginMember loginMember) {
        ProfileResponse response = profileService.findProfileByMemberId(loginMember.getId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/other/{otherMemberId}")
    @Operation(summary = "other/2", description = "타인 프로필을 조회한다.")
    public ResponseEntity<ProfileOtherResponse> findOtherProfileByMemberId(@Parameter(hidden = true) @AuthenticationPrincipal final LoginMember loginMember,
                                                                     @PathVariable final Long otherMemberId) {
      
        ProfileOtherResponse response = profileService.findOtherProfileByMemberId(otherMemberId);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/me/{profileId}")
    @Operation(summary = "/me/1", description = "본인 프로필을 수정한다.") // 닉네임, 나이, 성별, 성격, 자기소개, 직업 혹은 학교, 주소, 나의 사진
    public ResponseEntity<Void> update(@Parameter(hidden = true) @AuthenticationPrincipal final LoginMember loginMember,
                                       @Valid @RequestBody final ProfileUpdateRequest profileUpdateRequest) {
        profileService.updateProfile(loginMember.getId(), profileUpdateRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/nickname/exists", params = "nickname")
    public ResponseEntity<UniqueResponse> validateUniqueNickname(@RequestParam String nickname) {
        try {
            profileService.validateUniqueNickname(nickname);
            return ResponseEntity.ok(new UniqueResponse(true));
        } catch (NicknameAlreadyTakenException ex) {
            return ResponseEntity.ok(new UniqueResponse(false));
        }
    }
}