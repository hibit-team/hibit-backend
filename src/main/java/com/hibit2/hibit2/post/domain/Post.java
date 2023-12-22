package com.hibit2.hibit2.post.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hibit2.hibit2.global.config.BaseTimeEntity;
import com.hibit2.hibit2.member.domain.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

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
    @JsonIgnoreProperties({"hibernateLazyInitializer"})
    @JoinColumn(name = "member_idx")
    private Member member;


    @Column(length = 50, nullable = false)
    @Schema(description = "제목", example = "전시회 관람 같이가요")
    private String title;

    @Column(length = 200, nullable = false)
    @Schema(description = "본문", example = "본문내용내용")
    private String content;

    @Column(length = 255, nullable = false)
    @Schema(description = "전시회", example = "에드워드 호퍼: 길 위에서")
    private String exhibition;

    @Column(nullable = false)
    @Schema(description = "선호인원", example = "3")
    private int number;

    @Column(length = 100, nullable = false)
    @Schema(description = "오픈채팅url", example = "http://kakao~~")
    private String openchat;

    @Schema(description = "전시보고뭐할래", example = "EAT")
    @Enumerated(EnumType.STRING)
    private What_do what_do;

    @Column(nullable = false, columnDefinition = "integer default 0")
    @Schema(description = "조회수", example = "default 0 = 자동 카운트")
    private int view;

    @Column(nullable = false)
    @Schema(description = "글 상태 (작성 N, 삭제 D, 완료 C)", example = "N")
    private char status;

    @ElementCollection
    @CollectionTable(name = "post_date_time_slots", joinColumns = @JoinColumn(name = "post_idx"))
    @Schema(description = "신청 날짜", example = "[\n" + "{\n" +
            "    \"date\": \"2023-05-31\",\n" +
            "    \"timeSlot\": \"AM\"\n" +
            "  }" +"\n]" )
    private List<DateTimeSlot> dateTimeSlots;

    @Column(length = 300, nullable = true)
    @Schema(description = "대표이미지 url", example = "http://hibitbucket")
    private String mainimg;

    //나머지 이미지
    @ElementCollection
    @Schema(description = "나머지이미지 url 리스트", example = "http://hibitbucket")
    private List<String> subimg;


    @Column(nullable = false, columnDefinition = "integer default 0")
    @Schema(description = "좋아요 수", example = "default 0")
    private int liked;

    // 좋아요 누른 유저
    @ManyToMany
    @JoinTable(
            name = "post_likes",
            joinColumns = @JoinColumn(name = "post_idx"),
            inverseJoinColumns = @JoinColumn(name = "member_idx")
    )
    private List<Member> likeUsers = new ArrayList<>();

    @Column(nullable = false, columnDefinition = "integer default 0")
    @Schema(description = "댓글수", example = "15")
    private int comment_number;

    @Column(nullable = false, columnDefinition = "integer default 0")
    @Schema(description = "글 작성자가 초대장 보낸 횟수", example = "0")
    private int round;

    @Builder
    public Post(Member member,String title, String content, String exhibiton, int number, String openchat,
                int view, char status, What_do what_do, List<DateTimeSlot> dateTimeSlots, String mainimg,
                List<String> subimg, int liked,
                List<Member> likeUsers, int comment_number, int round){
        this.member=member;
        this.title=title;
        this.content=content;
        this.exhibition=exhibiton;
        this.number=number;
        this.openchat=openchat;
        this.view=view;
        this.status=status;
        this.what_do=what_do;
        this.dateTimeSlots=dateTimeSlots;
        this.mainimg=mainimg;
        this.subimg=subimg;
        this.liked=liked;
        this.likeUsers =likeUsers;
        this.comment_number = comment_number;
        this.round = round;
    }

    public void update(String title, String content, String exhibition, int number, String openchat, What_do what_do,List<DateTimeSlot> dateTimeSlots, String mainimg, List<String> subimg){
        this.title=title;
        this.content=content;
        this.exhibition = exhibition;
        this.number=number;
        this.openchat=openchat;
        this.what_do=what_do;
        this.dateTimeSlots=dateTimeSlots;
        this.mainimg=mainimg;
        this.subimg=subimg;
    }
    public void increaseView(){
        this.view++;
    }
    public void delete(){
        this.status = 'D';
    }
    public void complete(){
        this.status = 'C';
    }
    public void cancle(){
        this.status = 'A';
    }
    public void makeMainimg(String url){this.mainimg= url;}
    public void makeSubimg(List<String> url){this.subimg= url;}
    public void increaseLike(){
        this.liked++;
    }
    public void decreaseLike() {
        this.liked--;
    }
    public void increaseCommentNumber(){
        this.comment_number++;
    }
    public void decreaseCommentNumber(int count){
        this.comment_number= this.comment_number-count;
    }
    public void increaseRound(){
        this.round++;
    }
}
