package com.hibit2.hibit2.dto;

import com.hibit2.hibit2.domain.Comment;
import com.hibit2.hibit2.domain.Users;
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
/*
    public Comment toComment() {
        Comment comment = new Comment();
        comment.setIdx(this.idx);
        comment.setContent(this.content);
        comment.setLiked(this.liked);

        if (this.childComments != null) {
            List<Comment> childCommentList = new ArrayList<>();
            for (CommentListDto childCommentDto : this.childComments) {
                childCommentList.add(childCommentDto.toComment());
            }
            comment.setChildComments(childCommentList);
        }

        if (this.likeUsers != null) {
            List<Users> likeUsersList = new ArrayList<>();
            for (UserlistDto likeUserDto : this.likeUsers) {
                likeUsersList.add(likeUserDto.toUser());
            }
            comment.setLikeUsers(likeUsersList);
        }

        return comment;
    }*/

}
