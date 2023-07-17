package com.hibit2.hibit2.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hibit2.hibit2.domain.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.hibit2.hibit2.BaseTimeEntity;
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
    }

    private List<Object> number_and_What(int number, What_do what_do) {
        List<Object> number_and_What = new ArrayList<>();
        number_and_What.add(number+"인 관람");
        number_and_What.add(what_do);
        return number_and_What;
    }

}
