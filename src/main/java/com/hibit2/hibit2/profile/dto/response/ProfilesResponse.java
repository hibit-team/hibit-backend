package com.hibit2.hibit2.profile.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public class ProfilesResponse {

    private List<ProfileResponse> profiles;

    public ProfilesResponse(List<ProfileResponse> profiles) {
        this.profiles = profiles;
    }
}
