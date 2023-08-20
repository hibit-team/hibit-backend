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
@Transactional
public class ProfileService {

    private final MemberRepository memberRepository;
    private final ProfileRepository profileRepository;

    public ProfileService(MemberRepository memberRepository, ProfileRepository profileRepository) {
        this.memberRepository = memberRepository;
        this.profileRepository = profileRepository;
    }

    public ProfileRegisterResponse saveProfile(Long memberId, ProfileRegisterRequest request) {
        Member foundMember = memberRepository.getById(memberId);

        // 닉네임 중복 여부 검사하여 예외 메시지 추가
        if (profileRepository.existsByNickname(request.getNickname())) {
            throw new NicknameAlreadyTakenException("이미 사용 중인 닉네임입니다.");
        }

        Profile newProfile = createProfile(foundMember, request);
        updateMemberInfo(foundMember, newProfile);

        return new ProfileRegisterResponse(newProfile);
    }

    private Profile createProfile(Member member, ProfileRegisterRequest request) {
        return profileRepository.save(Profile.builder()
                .member(member)
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
                .build());
    }

    private void updateMemberInfo(Member member, Profile profile) {
        member.setNickname(profile.getNickname());
        member.setMainImg(profile.getMainImg());
        memberRepository.save(member);
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


    public ProfileOtherResponse findOtherProfile(Long otherMemberId) {
        Profile profile = profileRepository.findByMemberId(otherMemberId)
                .orElseThrow(() -> new NotFoundProfileException("타인의 프로필을 찾을 수 없습니다."));

        return new ProfileOtherResponse(profile);
    }

    public Profile findProfileById(Long profileId) {
        return profileRepository.findById(profileId)
                .orElseThrow(() -> new NotFoundProfileException("ID : " + profileId + " 에 해당하는 사용자가 없습니다."));
    }
    public void updateProfile(final Long memberId, final ProfileUpdateRequest request) {
        Profile profile = profileRepository.findByMemberId(memberId)
                .orElseThrow(() -> new NotFoundProfileException("프로필을 찾을 수 없습니다."));

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

    public boolean existsOtherProfileWithNickname(Long memberId, String nickname) {
        // 해당 멤버의 다른 프로필 중 닉네임이 같은 것이 있는지 확인
        return profileRepository.existsByMemberIdAndNickname(memberId, nickname);
    }

    public ProfileResponse findProfileByMemberId(Long memberId) {
        Profile profile = profileRepository.findByMemberId(memberId)
                .orElseThrow(() -> new NotFoundProfileException("프로필을 찾을 수 없습니다."));

        return new ProfileResponse(profile);
    }
}