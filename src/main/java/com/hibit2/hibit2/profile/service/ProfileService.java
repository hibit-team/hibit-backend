package com.hibit2.hibit2.profile.service;

import com.hibit2.hibit2.auth.dto.LoginMember;
import com.hibit2.hibit2.profile.dto.response.ProfilesResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hibit2.hibit2.member.domain.Member;
import com.hibit2.hibit2.member.repository.MemberRepository;
import com.hibit2.hibit2.profile.domain.Profile;
import com.hibit2.hibit2.profile.dto.request.ProfileRegisterRequest;
import com.hibit2.hibit2.profile.dto.request.ProfileUpdateRequest;
import com.hibit2.hibit2.profile.dto.response.ProfileRegisterResponse;
import com.hibit2.hibit2.profile.dto.response.ProfileResponse;
import com.hibit2.hibit2.profile.repository.ProfileRepository;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional(readOnly = true)
public class ProfileService {

    private final MemberRepository memberRepository;
    private final ProfileRepository profileRepository;

    public ProfileService(MemberRepository memberRepository, ProfileRepository profileRepository) {
        this.memberRepository = memberRepository;
        this.profileRepository = profileRepository;
    }

    @Transactional
    public ProfileRegisterResponse save(final Long memberId, final ProfileRegisterRequest request) {
        Member foundMember = memberRepository.getById(memberId);
        Profile profile = createProfile(foundMember, request);
        Profile saveProfile = profileRepository.save(profile);
        return new ProfileRegisterResponse(saveProfile);
    }
    public Profile createProfile(Member foundMember, ProfileRegisterRequest request) {
        return Profile.builder()
                .member(foundMember)
                .nickname(request.getNickname())
                .age(request.getAge())
                .gender(request.getGender())
                .personality(request.getPersonality())
                .introduce(request.getIntroduce())
                .mainImg(request.getMainImg())
                .subImg(request.getSubImg())
                .job(request.getJob())
                .addressCity(request.getAddressCity())
                .addressDistinct(request.getAddressDistinct())
                .build();
    }

    public ProfileResponse findProfileByIdAndMemberId(LoginMember loginMember, Long profileId) {
        Profile profile = profileRepository.getByMemberIdAndProfileId(loginMember.getId(), profileId);
        return new ProfileResponse(profile);
    }

    public ProfilesResponse findProfilesByIdAndMemberId() {
        List<ProfileResponse> profileResponses = profileRepository.findAll()
                .stream()
                .map(ProfileResponse::new)
                .collect(Collectors.toList());
        return new ProfilesResponse(profileResponses);
    }
    @Transactional
    public void update(final Long memberId, final Long profileId, final ProfileUpdateRequest request) {
        Profile profile = profileRepository.getByMemberIdAndProfileId(memberId, profileId);

        profile.updateNickname(request.getNickname());
        profile.updateAge(request.getAge());
        profile.updateGender(request.getGender());
        profile.updatePersonality(request.getPersonality());
        profile.updateIntroduce(request.getIntroduce());
        profile.updateMainImg(request.getMainImg());
        profile.updateSubImg(request.getSubImg());
        profile.updateJob(request.getJob());
        profile.updateAddressCity(request.getAddressCity());
        profile.updateAddressDistinct(request.getAddressDistinct());

        profileRepository.save(profile);
    }
}
