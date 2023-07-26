package com.hibit2.hibit2.profile.dto.response;

import com.hibit2.hibit2.profile.domain.Profile;

public class ProfileRegisterResponse {

    private Long id;

    public ProfileRegisterResponse(int idx) {
        this.id = id;
    }

    public ProfileRegisterResponse(Profile profile) {
        this.id = profile.getId();
    }

    public Long getId() {
        return id;
    }
}
