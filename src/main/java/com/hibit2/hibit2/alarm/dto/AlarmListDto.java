package com.hibit2.hibit2.alarm.dto;


import com.hibit2.hibit2.alarm.domain.Alarm;
import com.hibit2.hibit2.alarm.domain.AlarmType;
import com.hibit2.hibit2.comment.dto.CommentListDto;
import com.hibit2.hibit2.user.dto.UserlistDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class AlarmListDto {
    private String nickname;
    private AlarmType type;
    private String imglink;
    private String content;
    private String time;
    private String url;
    private boolean reads;


    public AlarmListDto(Alarm entity){
        this.nickname = entity.getSender().getId();
        this.type = entity.getAlarmType();
        this.imglink = entity.getSender().getProfileImg();
        this.content=entity.getContent();
        this.time=entity.calculateTime();
        this.url=entity.getUrl();
        this.reads=entity.isReads();
    }


}
