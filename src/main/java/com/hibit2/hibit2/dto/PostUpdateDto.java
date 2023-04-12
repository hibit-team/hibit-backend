package com.hibit2.hibit2.dto;

import com.hibit2.hibit2.domain.Post_status;
import com.hibit2.hibit2.domain.What_do;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostUpdateDto {
    private String title;
    private String content;
    private Post_status post_status;
    private int number;
    private String openchat;
    private What_do what_do;


    @Builder
    public PostUpdateDto(String title, String content, Post_status post_status,
                         int number, String openchat, What_do what_do){
        this.title=title;
        this.content=content;
        this.post_status=post_status;
        this.number=number;
        this.openchat=openchat;
        this.what_do=what_do;
    }
}
