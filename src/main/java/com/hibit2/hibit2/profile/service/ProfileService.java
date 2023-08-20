package com.hibit2.hibit2.profile.service;

import com.hibit2.hibit2.auth.dto.LoginMember;
import com.hibit2.hibit2.profile.dto.response.ProfileOtherResponse;
import com.hibit2.hibit2.profile.dto.response.ProfilesResponse;
import com.hibit2.hibit2.profile.exception.NicknameAlreadyTakenException;
import com.hibit2.hibit2.profile.exception.NotFoundProfileException;
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

    public ProfileRegisterResponse save(Long memberId, ProfileRegisterRequest request) {
        Member foundMember = memberRepository.getById(memberId);

        // 닉네임이 이미 존재한 경우라면 예외 처리 발생
        if (profileRepository.existsByNickname(request.getNickname())) {
            throw new NicknameAlreadyTakenException();
        }

        Profile profile1 = Profile.builder()
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
                .addressDistrict(request.getAddressDistrict())
                .build();
        Profile saveProfile = profileRepository.save(profile1);

        foundMember.setNickname(profile1.getNickname());
        foundMember.setMainImg(profile1.getMainImg());
        memberRepository.save(foundMember);

        return new ProfileRegisterResponse(saveProfile);
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


    public ProfileOtherResponse findOtherProfile(Long profileId) {
        Profile profile = findProfileById(profileId);

        return ProfileOtherResponse.from(profile);
    }

    public Profile findProfileById(Long profileId) {
        return profileRepository.findById(profileId)
                .orElseThrow(() -> new NotFoundProfileException("ID : " + profileId + " 에 해당하는 사용자가 없습니다."));
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
        profile.updateAddressDistinct(request.getAddressDistrict());

        profileRepository.save(profile);
    }
}