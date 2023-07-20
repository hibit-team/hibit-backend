package com.hibit2.hibit2.post.dto;

import com.hibit2.hibit2.post.domain.DateTimeSlot;
import com.hibit2.hibit2.post.domain.Post;
import com.hibit2.hibit2.post.domain.What_do;
import com.hibit2.hibit2.user.domain.Users;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.List;


@NoArgsConstructor
@Getter
public class PostSaveDto {
    @Schema(description = "유저", example = "소셜 로그인 후 수정 예정")
    private Users user;
    @Schema(description = "제목", example = "전시 관람해요")
    private String title;
    @Schema(description = "내용", example = "내용내용")
    private String content;
    @Schema(description = "전시회", example = "에드워드 호퍼: 길 위에서")
    private String exhibition;
    @Schema(description = "매칭 상태", example = "Holding")
    private char status;
    @Schema(description = "전시관람 인원", example = "1")
    private int number;
    @Schema(description = "오픈채팅 url", example = "http://open")
    private String openchat;
    @Schema(description = "전시이후뭐할래", example = "EAT")
    private What_do what_do;

    @Schema(description = "관람일자", example = "[\n" + "{\n" +
            "    \"date\": \"2023-05-31\",\n" +
            "    \"timeSlot\": \"AM\"\n" +
            "  }" +"\n]")
    private List<DateTimeSlot> dateTimeSlots;

    @Schema(description = "대표이미지 url", example = "http://hibitbucket")
    private String mainimg;
    @Schema(description = "나머지이미지 url 리스트", example = "[\n" +
            "        \"https://hibit2bucket.s3.ap-northeast-2.amazonaws.com/1.png\"\n" +
            "    ]")
    private List<String> subimg;
    @Builder
    public PostSaveDto(Users user, String title, String content, String exhibition, char status,
                       int number, String openchat, What_do what_do, List<DateTimeSlot> dateTimeSlots,
                       String mainimg, List<String>  subimg){
        this.user = user;
        this.title=title;
        this.content=content;
        this.exhibition=exhibition;
        this.status = status;
        this.number=number;
        this.openchat=openchat;
        this.what_do=what_do;
        this.dateTimeSlots = dateTimeSlots;
        this.mainimg=mainimg;
        this.subimg=subimg;
    }
    public Post toEntity(){
        return Post.builder()
                .user(user)
                .title(title)
                .content(content)
                .exhibiton(exhibition)
                .status('N')
                .number(number)
                .openchat(openchat)
                .what_do(what_do)
                .dateTimeSlots(dateTimeSlots)
                .mainimg(mainimg)
                .subimg(subimg)
                .build();
    }

    public void setUser(Users user) {this.user = user;}
}

