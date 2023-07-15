package com.hibit2.hibit2.profile.dto.response;

import com.hibit2.hibit2.profile.domain.Profile;

public class RegisterProfileResponse {

    private int id;

    public RegisterProfileResponse(int idx) {
        this.id = id;
    }

    public RegisterProfileResponse(Profile profile) {
        this.id = profile.getIdx();
    }

    public int getId() {
        return id;
    }
}
