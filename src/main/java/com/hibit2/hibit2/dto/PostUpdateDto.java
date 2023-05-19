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
    private int number;
    private String openchat;
    private What_do what_do;
    private List<DateTimeSlot> dateTimeSlots;


    @Builder
    public PostUpdateDto(String title, String content, int number, String openchat, What_do what_do,  List<DateTimeSlot> dateTimeSlots){
        this.title=title;
        this.content=content;
        this.number=number;
        this.openchat=openchat;
        this.what_do=what_do;
        this.dateTimeSlots=dateTimeSlots;
    }
}
