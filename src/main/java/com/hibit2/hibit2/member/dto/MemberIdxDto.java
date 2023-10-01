package com.hibit2.hibit2.member.dto;

import com.hibit2.hibit2.member.domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
public class MemberIdxDto {
    private Long idx;
    private boolean Isprofile;

    public MemberIdxDto(@NotNull Member entity){
        this.idx = entity.getId();
        this.Isprofile=entity.getIsprofile();
    }

}
