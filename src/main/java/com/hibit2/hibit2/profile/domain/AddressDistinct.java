package com.hibit2.hibit2.profile.domain;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AddressDistinct {

    MANHATTAN(1, "맨하튼");

    private final int index;
    private final String name;
}
