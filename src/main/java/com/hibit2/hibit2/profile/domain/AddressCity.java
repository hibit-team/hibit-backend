package com.hibit2.hibit2.profile.domain;

import lombok.Getter;

@Getter
public enum AddressCity {

    SEOUL("서울"),
    BUSAN("부산"),
    INCHEON("인천"),
    DAEGU("대구"),
    GWANGJU("광주"),
    DAEJEON("대전"),
    ULSAN("울산"),
    SEJONG("세종"),
    GYEONGGI("경기"),
    GANGWON("강원"),
    CHUNGBUK("충북"),
    CHUNGNAM("충남"),
    JEONBUK("전북"),
    JEONNAM("전남"),
    GYEONGBUK("경북"),
    GYEONGNAM("경남"),
    JEJU("제주");

    private final String name;

    AddressCity(String name) {
        this.name = name;
    }
}
