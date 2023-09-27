package com.hibit2.hibit2.profile.dto.response;

import lombok.Getter;

// 중복된 닉네임에 대한 dto
@Getter
public class UniqueResponse {

    private boolean unique;

    public UniqueResponse() {
    }

    public UniqueResponse(boolean unique) {
        this.unique = unique;
    }
}
