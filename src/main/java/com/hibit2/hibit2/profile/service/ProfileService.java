package com.hibit2.hibit2.profile.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hibit2.hibit2.member.domain.Member;
import com.hibit2.hibit2.member.domain.MemberRepository;
import com.hibit2.hibit2.profile.domain.Profile;
import com.hibit2.hibit2.profile.dto.request.ProfileRegisterRequest;
import com.hibit2.hibit2.profile.dto.request.ProfileUpdateRequest;
import com.hibit2.hibit2.profile.dto.response.ProfileRegisterResponse;
import com.hibit2.hibit2.profile.dto.response.UserProfileResponse;
import com.hibit2.hibit2.profile.exception.NotFoundProfileException;
import com.hibit2.hibit2.profile.repository.ProfileRepository;


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

        Member member = memberRepository.getById(memberId);
        Profile profile = request.toEntity(member);
        Profile saveProfile = profileRepository.save(profile);
        return new ProfileRegisterResponse(saveProfile);
    }
    public UserProfileResponse findProfileById(final Long profileId) {
        Profile profile = profileRepository.findById(profileId)
            .orElseThrow(NotFoundProfileException::new);
        return new UserProfileResponse(profile);
    }

    @Transactional
    public void update(final Long memberId, final Long profileId, final ProfileUpdateRequest request) {
        Profile profile = profileRepository.getByMemberIdAndProfileId(memberId, profileId);

        profile.modifyProfile(request.getNickname(), request.getAge(), request.getGender(),
                                request.getPersonality(), request.getIntroduce(),
                                request.getMainImg(), request.getSubImg(), request.getJob(),
                                request.getAddressCity(), request.getAddressDistinct());
    }
}
