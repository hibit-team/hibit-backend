package com.hibit2.hibit2.profile.controller;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import com.hibit2.hibit2.auth.presentation.AuthenticationPrincipal;
import com.hibit2.hibit2.profile.domain.PersonalityType;
import com.hibit2.hibit2.profile.dto.response.ProfileOtherResponse;
import com.hibit2.hibit2.profile.dto.response.ProfilesResponse;
import com.hibit2.hibit2.profile.exception.InvalidPersonalityException;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    public ResponseEntity<ProfileRegisterResponse> save(@Parameter(hidden = true) @AuthenticationPrincipal final LoginMember loginMember,
                                     @Valid @RequestBody final ProfileRegisterRequest profileRegisterRequest) {

        //프로필 등록 API에서 요청 데이터의 성격 정보를 검증하고 최대 5개까지만 선택되었는지 확인한다.
        List<PersonalityType> selectedPersonalities = profileRegisterRequest.getPersonality();
        if (selectedPersonalities.size() > 5) {
            throw new InvalidPersonalityException();
        }

        // 요청 데이터가 유효한 경우, 프로필을 저장하기 전에 최대 5개의 성격 정보만 선택하여 저장합니다.
        List<PersonalityType> validatedPersonalities = selectedPersonalities.subList(0, Math.min(5, selectedPersonalities.size()));

        // 검증된 성격을 가진 새로운 프로필을 생성한다.
        ProfileRegisterRequest validatedRequest = new ProfileRegisterRequest(
                profileRegisterRequest.getNickname(),
                profileRegisterRequest.getAge(),
                profileRegisterRequest.getGender(),
                validatedPersonalities,
                profileRegisterRequest.getIntroduce(),
                profileRegisterRequest.getMainImg(),
                profileRegisterRequest.getSubImg(),
                profileRegisterRequest.getJob(),
                profileRegisterRequest.getAddressCity(),
                profileRegisterRequest.getAddressDistrict()
        );

        //  프로필을 등록하고, 그에 대한 결과로 생성된 프로필 정보를 받아오는 부분
        ProfileRegisterResponse response = profileService.saveProfile(loginMember.getId(), validatedRequest);
        // 이 방식은 생성된 리소스의 URI를 반환하면서 동시에 응답 본문에 프로필 등록에 대한 세부 정보를 포함함.
        // 클라이언트는 프로필 정보를 추가 요청 없이 바로 확인할 수 있음.
        return ResponseEntity.created(URI.create("/api/profiles/" + response.getId())).body(response);
    }

    @GetMapping("/personalities")
    @Operation(summary = "/personalities",description = "사용자에게 선택할 수 있는 성격 목록을 반환합니다.")
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

    @GetMapping("/me/{profileId}")
    @Operation(summary = "/me/1", description = "본인 프로필을 조회한다.")
    public ResponseEntity<ProfileResponse> findProfileById(@AuthenticationPrincipal final LoginMember loginMember,
                                                           @PathVariable final Long profileId) {
        ProfileResponse response = profileService.findProfileByIdAndMemberId(loginMember, profileId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/other/{profileId}")
    @Operation(summary = "other/2", description = "타인 프로필을 조회한다.")
    public ResponseEntity<ProfileOtherResponse> findProfileByOtherId(@PathVariable Long profileId) {
        ProfileOtherResponse response = profileService.findOtherProfile(profileId);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/me/{profileId}")
    @Operation(summary = "/me/1", description = "프로필 수정")
    public ResponseEntity<Void> update(@Parameter(hidden = true) @AuthenticationPrincipal final LoginMember loginMember,
                                       @PathVariable final Long profileId,
                                       @Valid @RequestBody final ProfileUpdateRequest profileUpdateRequest) {
        profileService.update(loginMember.getId(), profileId, profileUpdateRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}