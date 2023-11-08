package com.hibit2.hibit2.profile.service;

import com.hibit2.hibit2.profile.dto.response.*;
import com.hibit2.hibit2.profile.exception.NicknameAlreadyTakenException;
import com.hibit2.hibit2.profile.exception.NotFoundProfileException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hibit2.hibit2.member.domain.Member;
import com.hibit2.hibit2.member.repository.MemberRepository;
import com.hibit2.hibit2.profile.domain.Profile;
import com.hibit2.hibit2.profile.dto.request.ProfileRegisterRequest;
import com.hibit2.hibit2.profile.dto.request.ProfileUpdateRequest;
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
                .jobVisible(request.getJobVisibility())
                .subImgVisible(request.getSubImgVisibility())
                .addressVisible(request.getAddressVisibility())
                .build());
    }

    private void updateMemberInfo(Member member, Profile profile) {
        member.setNickname(profile.getNickname());
        member.setMainImg(profile.getMainImg());
        member.setIsprofile();
        memberRepository.save(member);
    }

    public ProfilesResponse findProfilesByIdAndMemberId() {
        List<ProfileResponse> profileResponses = profileRepository.findAll()
                .stream()
                .map(ProfileResponse::new)
                .collect(Collectors.toList());
        return new ProfilesResponse(profileResponses);
    }

    public void updateProfile(final Long memberId, final ProfileUpdateRequest request) {

        Member member = memberRepository.getById(memberId);

        member.updateNickname(request.getNickname());
        member.updateMainImg(request.getMainImg());
        memberRepository.save(member);

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
        profile.updateJobVisible(request.getJobVisibility());
        profile.updateSubImgVisible(request.getSubImgVisibility());
        profile.updateAddressVisible(request.getAddressVisibility());
        profileRepository.save(profile);
    }

    public ProfileResponse findProfileByMemberId(Long memberId) {
        Profile profile = profileRepository.findByMemberId(memberId)
                .orElseThrow(() -> new NotFoundProfileException("프로필을 찾을 수 없습니다."));

        return new ProfileResponse(profile);
    }

    public ProfileOtherResponse findOtherProfileByMemberId(Long otherMemberId) {
        Profile profile = profileRepository.findByMemberId(otherMemberId)
                .orElseThrow(() -> new NotFoundProfileException("타인의 프로필을 찾을 수 없습니다."));

        return new ProfileOtherResponse(profile);
    }

    public void validateUniqueNickname(String nickname) {
        if (profileRepository.existsByNickname(nickname)) {
            throw new NicknameAlreadyTakenException("이미 사용중인 닉네임 입니다.");
        }
    }
}