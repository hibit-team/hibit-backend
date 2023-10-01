package com.hibit2.hibit2.comment.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.hibit2.hibit2.global.config.BaseTimeEntity;
import com.hibit2.hibit2.member.domain.Member;
import com.hibit2.hibit2.post.domain.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@DynamicInsert
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "idx")
public class Comment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idx;

    @Column(length = 251, nullable = false)
    @Schema(description = "댓글", example = "댓글내용")
    private String content;


    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer"})
    @JoinColumn(name = "post_idx")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer"})
    @JoinColumn(name = "member_idx")
    private Member member;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "parent_comment_id")
    private Comment parentComment;

    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> childComments = new ArrayList<>();

    //좋아요
    @Column(nullable = false, columnDefinition = "integer default 0")
    @Schema(description = "좋아요 수", example = "default 0")
    private int liked;

    // 좋아요 누른 유저
    @ManyToMany
    @JoinTable(
            name = "comment_likes",
            joinColumns = @JoinColumn(name = "comment_idx"),
            inverseJoinColumns = @JoinColumn(name = "member_idx")
    )
    private List<Member> likeUsers = new ArrayList<>();


    public void addChildComment(Comment childComment) {
        this.childComments.add(childComment);
        childComment.setParentComment(this);
    }

    public void increaseLike(){
        this.liked++;
    }
    public void decreaseLike() {
        this.liked--;
    }


}
