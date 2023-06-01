package com.hibit2.hibit2.profile.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AddressCity {

    NEW_YORK(1, "뉴욕");

    private final int index;
    private final String name;
}
