package com.hibit2.hibit2.dto;

import com.hibit2.hibit2.domain.Post;
import com.hibit2.hibit2.domain.Post_status;
import com.hibit2.hibit2.domain.Users;
import com.hibit2.hibit2.domain.What_do;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.hibit2.hibit2.domain.Post_status.Holding;

@NoArgsConstructor
@Getter
public class PostSaveDto {
    private Users user;
    private String title;
    private String content;
    private Post_status post_status;
    private int number;
    private String openchat;
    private What_do what_do;
    private char deleteYn;

    @Builder
    public PostSaveDto(Users user, String title, String content, Post_status post_status,
                       int number, String openchat, What_do what_do, char deleteYn){
        this.user = user;
        this.title=title;
        this.content=content;
        this.post_status = post_status;
        this.number=number;
        this.openchat=openchat;
        this.what_do=what_do;
        this.deleteYn = deleteYn;
    }
    public Post toEntity(){
        return Post.builder()
                .user(user)
                .title(title)
                .content(content)
                .post_status(Holding)
                .number(number)
                .openchat(openchat)
                .what_do(what_do)
                .deleteYn('N')
                .build();
    }

    public void setUser(Users user) {this.user = user;}
}

