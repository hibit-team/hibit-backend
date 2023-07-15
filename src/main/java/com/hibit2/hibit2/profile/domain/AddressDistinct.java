package com.hibit2.hibit2.profile.domain;


import lombok.Getter;

@Getter
public enum AddressDistinct {

    SEOUL_GANGNAM("서울 강남구"),
    SEOUL_JONGNO("서울 종로구"),
    SEOUL_YONGSAN("서울 용산구"),
    SEOUL_MAPO("서울 마포구"),
    SEOUL_GWANGJIN("서울 광진구"),
    SEOUL_DONGDAEMUN("서울 동대문구"),
    SEOUL_JUNG("서울 중구"),
    SEOUL_SONGPA("서울 송파구"),
    SEOUL_GANGDONG("서울 강동구"),
    SEOUL_GANGSEO("서울 강서구"),
    SEOUL_GANGBUK("서울 강북구"),
    SEOUL_SEODAEMUN("서울 서대문구"),
    SEOUL_SEOCHO("서울 서초구"),
    SEOUL_SEONGBUK("서울 성북구"),
    SEOUL_EUNPYEONG("서울 은평구"),
    SEOUL_GWANAK("서울 관악구"),
    SEOUL_DOBONG("서울 도봉구");

    private final String name;

    AddressDistinct(String name) {
        this.name = name;
    }
}
