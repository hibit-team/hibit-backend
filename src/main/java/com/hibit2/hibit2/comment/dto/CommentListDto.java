package com.hibit2.hibit2.comment.dto;


import com.hibit2.hibit2.comment.domain.Comment;
import com.hibit2.hibit2.user.domain.Users;
import com.hibit2.hibit2.user.dto.UserlistDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@Getter
public class CommentListDto {

    private int idx;
    private String writer;
    private String writerImg;
    private String content;
    private List<CommentListDto> childComments;
    private int liked;
    private String time;
    private List<UserlistDto> likeUsers;

    public CommentListDto(Comment entity) {
        this.idx = entity.getIdx();
        this.writer=entity.getUser().getId();
        this.writerImg=entity.getUser().getProfileImg();
        this.content = entity.getContent();
        this.childComments = new ArrayList<>();
        this.liked = entity.getLiked();
        this.time= entity.calculateTime();
        this.likeUsers = new ArrayList<>();

        if (entity.getChildComments() != null) {
            for (Comment childComment : entity.getChildComments()) {
                this.childComments.add(new CommentListDto(childComment));
            }
        }

        if (entity.getLikeUsers() != null) {
            for (Users likeUser : entity.getLikeUsers()) {
                this.likeUsers.add(new UserlistDto(likeUser));
            }
        }

    }

}
