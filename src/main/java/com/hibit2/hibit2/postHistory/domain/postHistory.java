package com.hibit2.hibit2.postHistory.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hibit2.hibit2.global.config.BaseTimeEntity;
import com.hibit2.hibit2.post.domain.Post;
import com.hibit2.hibit2.user.domain.Users;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@DynamicInsert
public class postHistory extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idx;


    @ManyToOne
    @JsonIgnoreProperties({"hibernateLazyInitializer"})
    @JoinColumn(name = "post_idx")
    private Post post;


    // 성공 유저
    @ElementCollection
    @Schema(description = "수락 유저 닉네임", example = "c, d")
    private List<String> okUsers = new ArrayList<>();

    @Column(nullable = true)
    @Schema(description = "완료 시간", example = "2023-07-23 19:24" )
    private LocalDateTime finishTime;


    @Column(nullable = false, columnDefinition = "integer default 0")
    @Schema(description = "수락 개수", example = "default 0")
    private int okNum;

    @Column(nullable = false, columnDefinition = "integer default 0")
    @Schema(description = "거절 개수", example = "default 0")
    private int noNum;

    @Column(nullable = false, columnDefinition = "integer default 0")
    @Schema(description = "비율", example = "50%")
    private float percent;

    @Builder
    public postHistory(Post post, List<String> okUsers, LocalDateTime finishTime,
                       int okNum, int noNum, float percent){
        this.post=post;
        this.okUsers=okUsers;
        this.finishTime=finishTime;
        this.okNum=okNum;
        this.noNum=noNum;
        this.percent=percent;
    }
    public void increaseOk() {this.okNum++;}
    public void increaseNo() {this.noNum++;}
    public void calculatePercent(int ok, int no) {this.percent = ok*100/(ok+no);}



}
