package com.hibit2.hibit2.alarm.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hibit2.hibit2.global.config.BaseTimeEntity;
import com.hibit2.hibit2.user.domain.Users;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@DynamicInsert
@Table(name = "alarm")

public class Alarm extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer"})
    @JoinColumn(name = "user_idx")
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer"})
    @JoinColumn(name = "sender_idx")
    private Users sender;


    @Schema(description = "알림 타입", example = "COMMENT")
    @Enumerated(EnumType.STRING)
    private AlarmType alarmType;

    @Column(length = 255,nullable = false)
    @Schema(description = "내용", example = "__님이 댓글을 작성했습니다.")
    private String content;

    @Column(nullable = true)
    @Schema(description = "오픈채팅방링크", example = "openchaturl")
    private String url;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    @Schema(description = "읽음 여부", example = "True")
    private boolean reads;

    @Builder
    public Alarm(Users user, AlarmType alarmType, String content, String url, boolean reads){
        this.user=user;
        this.alarmType = alarmType;
        this.content=content;
        this.url=url;
        this.reads=reads;
    }

    public void readAlarm() {this.reads = true;}

}
