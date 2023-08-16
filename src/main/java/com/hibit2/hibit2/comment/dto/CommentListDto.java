package com.hibit2.hibit2.comment.dto;


import com.hibit2.hibit2.comment.domain.Comment;
import com.hibit2.hibit2.member.domain.Member;
import com.hibit2.hibit2.member.dto.MemberListDto;
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
    private Long writerIdx;
    private String writerImg;
    private String content;
    private List<CommentListDto> childComments;
    private int liked;
    private String time;
    private List<MemberListDto> likeUsers;

    public CommentListDto(Comment entity) {
        this.idx = entity.getIdx();
        this.writer=entity.getMember().getNickname();
        this.writerIdx=entity.getMember().getId();
        this.writerImg=MainImg(entity.getMember());
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
            for (Member likeUser : entity.getLikeUsers()) {
                this.likeUsers.add(new MemberListDto(likeUser));
            }
        }

    }

    private String MainImg(Member member){
        if (member.getMainImg() != null) {
            return member.getMainImg();
        }
        return "https://hibit2bucket.s3.ap-northeast-2.amazonaws.com/Group%201181.png";
    }

}
