package com.hibit2.hibit2.global.config;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.Duration;
import java.time.LocalDateTime;

@ToString
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseTimeEntity {
    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    public String calculateTime() {
        LocalDateTime currentTime = LocalDateTime.now();
        Duration duration = Duration.between(createdDate, currentTime);
        long hours = duration.toHours();
        if (hours < 1) {
            long minutes = duration.toMinutes();
            return minutes + "분 전";
        } else if (hours < 24) {
            return hours + "시간 전";
        } else {
            long days = duration.toDays();
            return days + "일 전";
        }
    }

}