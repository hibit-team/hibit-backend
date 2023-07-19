package com.hibit2.hibit2.post.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hibit2.hibit2.post.domain.DateTimeSlot;
import com.hibit2.hibit2.post.domain.Post;
import com.hibit2.hibit2.post.domain.TimeSlot;
import com.hibit2.hibit2.post.domain.What_do;
import com.hibit2.hibit2.user.domain.Users;
import com.hibit2.hibit2.global.config.BaseTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
public class PostListDto {
    private int idx;
    @JsonIgnoreProperties({"hibernateLazyInitializer"})
    private Users user;
    private String title;
    private String exhibition;
    private char status;
    private List<Object> number_and_What;
    private String mainimg;
    private int liked;
    private int comment_number;
    private String dateTime;


    public PostListDto(@NotNull Post entity){
        this.idx=entity.getIdx();
        this.user = entity.getUser();
        this.title=entity.getTitle();
        this.exhibition=entity.getExhibition();
        this.status=entity.getStatus();
        this.number_and_What = number_and_What(entity.getNumber(), entity.getWhat_do());
        this.mainimg=entity.getMainimg();
        this.liked=entity.getLiked();
        this.comment_number=entity.getComment_number();
        this.dateTime = formatDateTimeSlots(entity.getDateTimeSlots());
    }

    private List<Object> number_and_What(int number, What_do what_do) {
        List<Object> number_and_What = new ArrayList<>();
        number_and_What.add(number+"인 관람");
        number_and_What.add(what_do);
        return number_and_What;
    }
    private String formatDateTimeSlots(List<DateTimeSlot> dateTimeSlots) {
        if (dateTimeSlots.isEmpty()) {
            return "";
        }

        DateTimeSlot earliestDateTimeSlot = dateTimeSlots.get(0);
        String earliestDate = earliestDateTimeSlot.getDate().toString();
        TimeSlot earliestTimeSlot = earliestDateTimeSlot.getTimeSlot();

        int remainingCount = dateTimeSlots.size() - 1;

        return earliestDate + " " + earliestTimeSlot.toString() + " 외 " + remainingCount + "개";
    }
}
