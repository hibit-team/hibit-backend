package com.hibit2.hibit2.post.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.hibit2.hibit2.member.domain.Member;
import com.hibit2.hibit2.post.domain.DateTimeSlot;
import com.hibit2.hibit2.post.domain.Post;
import com.hibit2.hibit2.post.domain.TimeSlot;
import com.hibit2.hibit2.post.domain.What_do;
import com.hibit2.hibit2.user.domain.Users;
import com.hibit2.hibit2.user.dto.UserlistDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
public class PostResponseDto {
    private int idx;
    private String writer;
    private Long writerIdx;
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
    private List<String> dateTime;
    private List<UserlistDto> likeUsers;


    public PostResponseDto(@NotNull Post entity){
        this.idx=entity.getIdx();
        this.writer=entity.getMember().getNickname();
        this.writerIdx=entity.getMember().getId();
        this.writerImg=MainImg(entity.getMember());
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
        this.dateTime = formatDateTimeSlots(entity.getDateTimeSlots());
        this.likeUsers= new ArrayList<>();


        if (entity.getLikeUsers() != null) {
            for (Users likeUser : entity.getLikeUsers()) {
                this.likeUsers.add(new UserlistDto(likeUser));
            }
        }

    }
    private String MainImg(Member member){
        if (member.getMainImg() != null) {
            return member.getMainImg();
        }
        return "https://hibit2bucket.s3.ap-northeast-2.amazonaws.com/Group%201181.png";
    }

    private List<Object> number_and_What(int number, What_do what_do) {
        List<Object> number_and_What = new ArrayList<>();
        number_and_What.add(number+"인 관람");
        number_and_What.add(what_do.getDecs());
        return number_and_What;
    }


    private List<String> formatDateTimeSlots(List<DateTimeSlot> dateTimeSlots) {
        List<String> formattedSlots = new ArrayList<>();

        for (DateTimeSlot dateTimeSlot : dateTimeSlots) {
            LocalDate date = dateTimeSlot.getDate();
            TimeSlot timeSlot = dateTimeSlot.getTimeSlot();

            String formattedDate = date.toString();
            String formattedTimeSlot = timeSlot == TimeSlot.AM ? "오전" : "오후";

            formattedSlots.add(formattedDate + " " + formattedTimeSlot);
        }

        return formattedSlots;
    }


}
