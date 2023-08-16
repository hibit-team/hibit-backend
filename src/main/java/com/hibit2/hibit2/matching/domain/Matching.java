package com.hibit2.hibit2.matching.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hibit2.hibit2.member.domain.Member;
import com.hibit2.hibit2.post.domain.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Matching {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer"})
    @JoinColumn(name = "member_idx")
    private Member member;


    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @Enumerated(EnumType.STRING)
    private MatchStatus status;

    @Column(nullable = true)
    @Schema(description = "글 작성자가 초대장 보낸 순서", example = "2")
    private int round;

    public Matching(Member member, Post post) {
        this.member = member;
        this.post = post;
        this.status = MatchStatus.HOLDING;
        this.round = round;
    }

    public void setPost(Post post) {this.post = post;}
    public void setMember(Member member) {this.member = member;}


    public void setRound(int round) {this.round = round;}

}
