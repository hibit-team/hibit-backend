package com.hibit2.hibit2.alarm.controller;

import com.hibit2.hibit2.alarm.service.AlarmService;
import com.hibit2.hibit2.post.domain.DateTimeSlot;
import com.hibit2.hibit2.post.domain.Post;
import com.hibit2.hibit2.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Component
@RestController
@RequiredArgsConstructor
public class AlarmScheduler {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private AlarmService alarmService;
    @Scheduled(cron = "0 0 0 * * *") //자정
    @Transactional
    public void sendAlarm() {
        LocalDate currentDate = LocalDate.now();
        LocalDate targetDate = currentDate.minusDays(1);
        List<Post> postsDate = postRepository.findPostsByDateTimeSlotsDate(targetDate);

        for (Post post : postsDate) {
            Hibernate.initialize(post.getDateTimeSlots());
            List<DateTimeSlot> dateTimeSlots = post.getDateTimeSlots();
            for (DateTimeSlot dateTimeSlot : dateTimeSlots) {
                if (dateTimeSlot.getDate().equals(targetDate)) {
                    alarmService.createRemind(post.getMember());
                    break; //오전,오후 하나만 보내기
                }
            }
        }

    }
}
