package com.hibit2.hibit2.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hibit2.hibit2.BaseTimeEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@DynamicInsert
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idx;

    @ManyToOne(fetch = FetchType.LAZY)
    //@JsonIgnore
    @JoinColumn(name = "user_idx")
    private Users user;



    @Column(length = 50, nullable = false)
    @Schema(description = "제목", example = "전시회 관람 같이가요")
    private String title;

    @Column(length = 200, nullable = false)
    @Schema(description = "본문", example = "본문내용내용")
    private String content;

    @Column(nullable = false)
    @Schema(description = "게시글 상태", example = "Holding, Pending, Completed")
    private Post_status post_status;

    @Column(nullable = false)
    @Schema(description = "선호인원", example = "3")
    private int number;

    @Column(length = 100, nullable = false)
    @Schema(description = "오픈채팅url", example = "http://kakao~~")
    private String openchat;

    @Column(nullable = false)
    @Schema(description = "전시보고뭐할래", example = "[EAT, CAFE]")
    @ElementCollection(targetClass = What_do.class)
    @Enumerated(EnumType.STRING)
    private List<What_do> what_do;


    @Column(nullable = false, columnDefinition = "integer default 0")
    @Schema(description = "조회수", example = "default 0 = 자동 카운트")
    private int view;

    @Column(nullable = false)
    @Schema(description = "삭제여부", example = "default N (작성 N, 삭제 Y)")
    private char deleteYn;

    @ElementCollection
    @CollectionTable(name = "post_date_time_slots", joinColumns = @JoinColumn(name = "post_idx"))
    private List<DateTimeSlot> dateTimeSlots;

    @Column(length = 300, nullable = true)
    @Schema(description = "대표이미지 idx", example = "1")
    private String mainimg;

    @Column(nullable = false, columnDefinition = "integer default 0")
    @Schema(description = "좋아요 수", example = "default 0")
    private int liked;

    // 좋아요 누른 유저
    @ManyToMany
    @JoinTable(
            name = "post_likes",
            joinColumns = @JoinColumn(name = "post_idx"),
            inverseJoinColumns = @JoinColumn(name = "user_idx")
    )
    private List<Users> likeUsers = new ArrayList<>();

    @Builder
    public Post(Users user,String title, String content, Post_status post_status, int number, String openchat,
                int view, char deleteYn, List<What_do> what_do, List<DateTimeSlot> dateTimeSlots, String mainimg, int liked,
                List<Users> likeUsers){
        this.user=user;
        this.title=title;
        this.content=content;
        this.post_status=post_status;
        this.number=number;
        this.openchat=openchat;
        this.view=view;
        this.deleteYn=deleteYn;
        this.what_do=what_do;
        this.dateTimeSlots=dateTimeSlots;
        this.mainimg=mainimg;
        this.liked=liked;
        this.likeUsers =likeUsers;
    }

    public void update(String title, String content, int number, String openchat, List<What_do> what_do,List<DateTimeSlot> dateTimeSlots, String mainimg){
        this.title=title;
        this.content=content;
        this.number=number;
        this.openchat=openchat;
        this.what_do=what_do;
        this.dateTimeSlots=dateTimeSlots;
        this.mainimg=mainimg;
    }
    public void increaseView(){
        this.view++;
    }
    public void delete(){
        this.deleteYn = 'Y';
    }

    public void makeMainimg(String url){this.mainimg= url;}

    public void increaseLike(){
        this.liked++;
    }
    public void decreaseLike() {
        this.liked--;
    }

}
