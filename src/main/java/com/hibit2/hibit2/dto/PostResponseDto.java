package com.hibit2.hibit2.dto;

import com.hibit2.hibit2.domain.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
public class PostResponseDto {
    private int idx;
    private Users user;
    private String title;
    private String content;
    private Post_status post_status;
    private int number;
    private String openchat;
    private What_do what_do;
    private int view;
    private LocalDateTime createdDate;
    private char deleteYn;
    private List<DateTimeSlot> dateTimeSlots;
    private int mainimg;

    public PostResponseDto(@NotNull Post entity){
        this.idx=entity.getIdx();
        this.user = entity.getUser();
        this.title=entity.getTitle();
        this.content=entity.getContent();
        this.post_status=entity.getPost_status();
        this.number=entity.getNumber();
        this.openchat=entity.getOpenchat();
        this.what_do=entity.getWhat_do();
        this.view=entity.getView();
        this.createdDate = entity.getCreatedDate();
        this.deleteYn = entity.getDeleteYn();
        this.dateTimeSlots = entity.getDateTimeSlots();  // 매개변수에서 전달받은 dateTimeSlots를 사용
        this.mainimg=entity.getMainimg();
    }

}
