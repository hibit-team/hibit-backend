package com.hibit2.hibit2.post.dto;


import com.hibit2.hibit2.post.domain.DateTimeSlot;
import com.hibit2.hibit2.post.domain.What_do;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class PostUpdateDto {
    private String title;
    private String content;
    private String exhibiton;
    private int number;
    private String openchat;
    private What_do what_do;
    private List<DateTimeSlot> dateTimeSlots;
    private String mainimg;


    @Builder
    public PostUpdateDto(String title, String content, String exhibiton, int number, String openchat, What_do what_do,  List<DateTimeSlot> dateTimeSlots,String mainimg){
        this.title=title;
        this.content=content;
        this.exhibiton=exhibiton;
        this.number=number;
        this.openchat=openchat;
        this.what_do=what_do;
        this.dateTimeSlots=dateTimeSlots;
        this.mainimg=mainimg;
    }
}
