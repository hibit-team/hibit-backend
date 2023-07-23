package com.hibit2.hibit2.profile.dto.response;

import com.hibit2.hibit2.profile.domain.Profile;

public class RegisterProfileResponse {

    private Long id;

    public RegisterProfileResponse(int idx) {
        this.id = id;
    }

    public RegisterProfileResponse(Profile profile) {
        this.id = profile.getId();
    }

    public Long getId() {
        return id;
    }
}
