package com.hibit2.hibit2.dto;

import com.hibit2.hibit2.domain.*;
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
    private Users user;
    private String title;
    private Post_status post_status;
    private List<Object> number_and_What;
    private int view;
    private String mainimg;

    public PostListDto(@NotNull Post entity){
        this.idx=entity.getIdx();
        this.user = entity.getUser();
        this.title=entity.getTitle();
        this.post_status=entity.getPost_status();
        this.number_and_What = number_and_What(entity.getNumber(), entity.getWhat_do());
        this.view=entity.getView();
        this.mainimg=entity.getMainimg();
    }

    private List<Object> number_and_What(int number, List<What_do> what_do) {
        List<Object> number_and_What = new ArrayList<>();
        number_and_What.add(number+"인 관람");
        List<String> whatDoDescList = what_do.stream()
                .map(What_do::getDecs)
                .collect(Collectors.toList());
        number_and_What.addAll(whatDoDescList);
        return number_and_What;
    }

}
