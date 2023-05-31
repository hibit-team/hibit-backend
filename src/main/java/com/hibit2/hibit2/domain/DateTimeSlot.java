package com.hibit2.hibit2.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Embeddable;
import java.time.LocalDate;

@Embeddable
@Getter
public class DateTimeSlot {
    private LocalDate date;
    private TimeSlot timeSlot;
    protected DateTimeSlot() {}

    // 사용자 정의 생성자
    @Builder
    public DateTimeSlot(LocalDate date, TimeSlot timeSlot) {
        this.date = date;
        this.timeSlot = timeSlot;
    }
}