package com.hibit2.hibit2.dto;


import com.hibit2.hibit2.domain.Users;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor
@Getter
public class UserlistDto {
    private int idx;
    private String id;
    private String profileImg;

    public UserlistDto(@NotNull Users entity){
        this.idx=entity.getIdx();
        this.id=entity.getId();
        this.profileImg=entity.getProfileImg();
    }

    public void setIdx(int idx) {this.idx = idx;}
    public void setId(String id) {this.id = id;}
    public void setProfileImg(String profileImg) {this.profileImg = profileImg;}


}
