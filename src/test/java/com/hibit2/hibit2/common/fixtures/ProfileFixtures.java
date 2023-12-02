package com.hibit2.hibit2.common.fixtures;

import com.hibit2.hibit2.member.domain.Member;
import com.hibit2.hibit2.profile.domain.AddressCity;
import com.hibit2.hibit2.profile.domain.AddressDistrict;
import com.hibit2.hibit2.profile.domain.PersonalityType;
import com.hibit2.hibit2.profile.domain.Profile;

import java.util.List;

import static com.hibit2.hibit2.common.fixtures.MemberFixtures.*;
import static com.hibit2.hibit2.member.domain.SocialType.GOOGLE;

public class ProfileFixtures {

    /* 팬시 */
    public static final String 팬시_닉네임 = "팬시";
    public static final int 팬시_나이 = 20;
    public static final int 팬시_성별 = 0;
    public static final List<PersonalityType> 팬시_성격들 = List.of(PersonalityType.TYPE_1, PersonalityType.TYPE_2, PersonalityType.TYPE_3, PersonalityType.TYPE_4, PersonalityType.TYPE_5);
    public static final String 팬시_자기소개 = "안녕하세요. 팬시입니다.";
    public static final String 팬시_메인_이미지 = "fancy.png";
    public static final List<String> 팬시_서브_이미지 = List.of("fancy1.png", "fancy2.png", "fancy3.png");
    public static final String 팬시_직업 = "개발자";
    public static final AddressCity 팬시_도시 = AddressCity.SEOUL;
    public static final AddressDistrict 팬시_도시_구 = AddressDistrict.SEOUL_YONGSAN;
    public static final int 팬시_직업_선택여부 = 1;
    public static final int 팬시_서브_이미지_선택여부 = 1;
    public static final int 팬시_주소_선택여부 = 1;

    public static Profile 팬시_프로필() {
        return new Profile(new Member(팬시_이메일, 팬시_이름, 팬시_프로필, GOOGLE), 팬시_닉네임, 팬시_나이, 팬시_성별, 팬시_성격들,
                팬시_자기소개, 팬시_메인_이미지, 팬시_서브_이미지,
                팬시_직업, 팬시_도시, 팬시_도시_구,
                팬시_직업_선택여부, 팬시_서브_이미지_선택여부, 팬시_주소_선택여부);
    }
}
