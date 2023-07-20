package com.hibit2.hibit2.post.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.hibit2.hibit2.post.domain.DateTimeSlot;
import com.hibit2.hibit2.post.domain.Post;
import com.hibit2.hibit2.post.domain.What_do;
import com.hibit2.hibit2.user.domain.Users;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
public class PostResponseDto {
    private int idx;
    private String writer;
    private String writerImg;
    private String title;
    private String content;
    private String exhibiton;
    private char status;
    private int number;
    private String openchat;
    private int view;
    private LocalDateTime createdDate;
    private List<Object> number_and_What;
    private String mainimg;
    private List<String> subimg;
    private String time;

    public PostResponseDto(@NotNull Post entity){
        this.idx=entity.getIdx();
        this.writer=entity.getUser().getId();
        this.writerImg=entity.getUser().getProfileImg();
        this.title=entity.getTitle();
        this.exhibiton=entity.getExhibition();
        this.content=entity.getContent();
        this.status=entity.getStatus();
        this.number=entity.getNumber();
        this.openchat=entity.getOpenchat();
        this.view=entity.getView();
        this.createdDate = entity.getCreatedDate();
        this.number_and_What = number_and_What(entity.getNumber(), entity.getWhat_do());
        this.mainimg=entity.getMainimg();
        this.subimg=entity.getSubimg();
        this.time = entity.calculateTime();
    }

    private List<Object> number_and_What(int number, What_do what_do) {
        List<Object> number_and_What = new ArrayList<>();
        number_and_What.add(number+"인 관람");
        number_and_What.add(what_do.getDecs());
        return number_and_What;
    }

}