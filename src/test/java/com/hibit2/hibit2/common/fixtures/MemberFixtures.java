package com.hibit2.hibit2.common.fixtures;

import com.hibit2.hibit2.member.domain.Member;
import com.hibit2.hibit2.member.dto.MemberResponse;

import static com.hibit2.hibit2.member.domain.SocialType.GOOGLE;

public class MemberFixtures {

    /* 관리자 */
    public static final String 관리자_이메일 = "hibit.admin@gmail.com";
    public static final String 관리자_이름 = "관리자";
    public static final String 관리자_프로필 = "/hibit-admin.png";
    public static final MemberResponse 관리자_응답 = new MemberResponse(1L, 관리자_이메일, 관리자_이름, 관리자_프로필, GOOGLE);

    /* 팬시 */
    public static final String 팬시_이메일 = "fancy.junyongmoon@gmail.com";
    public static final String 팬시_이름 = "팬시";
    public static final String 팬시_프로필 = "/fancy.png";
    public static final MemberResponse 팬시_응답 = new MemberResponse(2L, 팬시_이메일, 팬시_이름, 팬시_프로필, GOOGLE);

    public static Member 관리자() {
        return new Member(관리자_이메일, 관리자_이름, 관리자_프로필, GOOGLE);
    }
    public static Member 팬시() {
        return new Member(팬시_이메일, 팬시_이름, 팬시_프로필, GOOGLE);
    }

}
