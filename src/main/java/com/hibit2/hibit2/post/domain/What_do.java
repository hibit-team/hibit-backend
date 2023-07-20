package com.hibit2.hibit2.post.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum What_do {
    EAT("맛집 가기"),
    CAFE("카페 가기"),
    ONLY("전시만 보기"),
    LATER("만나서 정해요!");

    private final String decs;
}
