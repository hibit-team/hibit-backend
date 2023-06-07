package com.hibit2.hibit2.dto;

import com.hibit2.hibit2.domain.DateTimeSlot;
import com.hibit2.hibit2.domain.Post_status;
import com.hibit2.hibit2.domain.What_do;
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
    private List<What_do> what_do;
    private List<DateTimeSlot> dateTimeSlots;
    private String mainimg;


    @Builder
    public PostUpdateDto(String title, String content, String exhibiton, int number, String openchat, List<What_do> what_do,  List<DateTimeSlot> dateTimeSlots,String mainimg){
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
