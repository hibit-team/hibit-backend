package com.hibit2.hibit2.profile.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hibit2.hibit2.profile.domain.Profile;
import com.hibit2.hibit2.profile.dto.request.RegisterProfileRequest;
import com.hibit2.hibit2.profile.dto.response.RegisterProfileResponse;
import com.hibit2.hibit2.profile.dto.response.UserProfileResponse;
import com.hibit2.hibit2.profile.exception.NotFoundProfileException;
import com.hibit2.hibit2.profile.repository.ProfileRepository;


@Service
@Transactional(readOnly = true)
public class ProfileService {

    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Transactional
    public RegisterProfileResponse registerProfile(RegisterProfileRequest registerProfileRequest) {
        Profile profile = Profile.builder()
            .nickname(registerProfileRequest.getNickname())
            .age(registerProfileRequest.getAge())
            .gender(registerProfileRequest.getGender())
            .personality(registerProfileRequest.getPersonalityTypeList())
            .introduce(registerProfileRequest.getIntroduce())
            .mainImg(registerProfileRequest.getMainImg())
            .job(registerProfileRequest.getJob())
            .addressCity(registerProfileRequest.getAddressCity())
            .addressDistinct(registerProfileRequest.getAddressDistinct())
            .build();

        Profile saveProfile = profileRepository.save(profile);
        return new RegisterProfileResponse(saveProfile);
    }

    public UserProfileResponse getProfile(int profileId) {
        Profile profile = profileRepository.findById(profileId)
            .orElseThrow(NotFoundProfileException::new);
        return new UserProfileResponse(profile);
    }
}
