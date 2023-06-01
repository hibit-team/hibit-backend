package com.hibit2.hibit2.profile.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PersonalityType {

    TYPE_A(1, "활발한"),
    TYPE_B(2, "건강한");

    private final int index;
    private final String contents;


}
