package com.hibit2.hibit2.member.dto;


import com.hibit2.hibit2.member.domain.Member;
import com.hibit2.hibit2.user.domain.Users;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
public class MemberListDto {
    private Long idx;
    private String id;
    private String profileImg;


    public MemberListDto(@NotNull Member entity){
        this.idx=entity.getId();
        this.id=entity.getNickname();
        this.profileImg=entity.getMainImg();
    }


    public void setIdx(Long idx) {this.idx = idx;}
    public void setId(String id) {this.id = id;}
    public void setMainimg(String profileImg) {this.profileImg = profileImg;}


}
