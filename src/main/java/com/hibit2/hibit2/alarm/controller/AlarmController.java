package com.hibit2.hibit2.alarm.controller;


import com.hibit2.hibit2.alarm.domain.Alarm;
import com.hibit2.hibit2.alarm.domain.AlarmType;
import com.hibit2.hibit2.alarm.dto.AlarmListDto;
import com.hibit2.hibit2.alarm.repository.AlarmRepository;
import com.hibit2.hibit2.alarm.service.AlarmService;
import com.hibit2.hibit2.auth.dto.LoginMember;
import com.hibit2.hibit2.auth.presentation.AuthenticationPrincipal;
import com.hibit2.hibit2.member.domain.Member;
import com.hibit2.hibit2.member.repository.MemberRepository;
import com.hibit2.hibit2.user.domain.Users;
import com.hibit2.hibit2.user.repository.UsersRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Tag(name = "alarm", description = "알람")
@RestController
@RequiredArgsConstructor

@RequestMapping("/alarm")
public class AlarmController {
    private final AlarmService alarmService;
    private final AlarmRepository alarmRepository;
    private final MemberRepository memberRepository;

    @PostMapping("/make")
    @Operation(summary = "alarm/make", description = "모든 유저에게 알림 생성(이벤트)")
    public ResponseEntity<Alarm> save(@RequestBody String content){

        Member sender = memberRepository.findByEmail("teamhibit@gmail.com")
                .orElseThrow(() -> new RuntimeException("관리자를 찾을 수 없습니다."));

        List<Member> allMembers = memberRepository.findAll();

        for (Member member : allMembers) {
            Alarm alarm = new Alarm();
            alarm.setReceiver(member);
            alarm.setSender(sender);
            alarm.setAlarmType(AlarmType.EVENT);
            alarm.setUrl("");
            alarm.setContent(content);
            alarmRepository.save(alarm);
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @GetMapping("/list")
    @Operation(summary = "alarm/list", description = "해당 유저 알람 전체 리스트")
    public ResponseEntity<List<AlarmListDto>> getAlarmList(@Parameter(hidden = true) @AuthenticationPrincipal final LoginMember loginMember){
        List<Alarm> alarms = alarmService.getAlarmByMemberId(loginMember.getId());
        alarms.sort(Comparator.comparing(Alarm::getCreatedDate).reversed());
        List<AlarmListDto> alarmListDtos = new ArrayList<>();
        for (Alarm alarm : alarms){
            AlarmListDto alarmListDto = new AlarmListDto(alarm);
            alarmListDtos.add(alarmListDto);
        }
        return ResponseEntity.ok(alarmListDtos);
    }

    @PutMapping("/read/{alarm_idx}")
    @Operation(summary = "alarm/read/1", description = "알람 읽음 처리")
    public ResponseEntity<Void> alarmRead(@Parameter(hidden = true) @AuthenticationPrincipal final LoginMember loginMember, @PathVariable int alarm_idx){
        Alarm alarm = alarmRepository.findById(alarm_idx)
                .orElseThrow(() -> new RuntimeException("를 찾을 수 없습니다."));
        if (alarm.getReceiver().getId() == loginMember.getId()){
            alarm.readAlarm();
            alarmRepository.save(alarm);
            return ResponseEntity.ok().build();
        }
        else{
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PostMapping("/dummy")
    public void DummyData() {
        Member sender = memberRepository.findById(1L).orElse(null);
        Member receiver = memberRepository.findById(3L).orElse(null);

        if (sender != null && receiver != null) {
            Alarm alarm1 = new Alarm();
            alarm1.setReceiver(receiver);
            alarm1.setSender(sender);
            alarm1.setPostIdx(1);
            //alarm1.setMatchingIdx(0);
            alarm1.setAlarmType(AlarmType.COMMENT);
            alarm1.setContent("더미 알림 1");
            alarm1.setUrl("더미 URL 1");
            alarm1.setReaded(false);
            alarm1.setHistory("N");

            Alarm alarm2 = new Alarm();
            alarm2.setReceiver(receiver);
            alarm2.setSender(sender);
            alarm2.setPostIdx(1);
            alarm2.setMatchingIdx(0);
            alarm2.setAlarmType(AlarmType.RECOMMENT);
            alarm2.setContent("더미 알림 2");
            alarm2.setUrl("더미 URL 2");
            alarm2.setReaded(false);
            alarm2.setHistory("N");

            Alarm alarm3 = new Alarm();
            alarm3.setReceiver(receiver);
            alarm3.setSender(sender);
            alarm3.setPostIdx(1);
            //alarm1.setMatchingIdx(0);
            alarm3.setAlarmType(AlarmType.COMMENTHEART);
            alarm3.setContent("더미 알림 3");
            alarm3.setUrl("더미 URL 3");
            alarm3.setReaded(false);
            alarm3.setHistory("N");


            Alarm alarm4 = new Alarm();
            alarm4.setReceiver(receiver);
            alarm4.setSender(sender);
            alarm4.setPostIdx(1);
            alarm4.setMatchingIdx(0);
            alarm4.setAlarmType(AlarmType.INVITATION);
            alarm4.setContent("더미 알림 4");
            alarm4.setUrl("더미 URL 4");
            alarm4.setReaded(false);
            alarm4.setHistory("N");


            Alarm alarm5 = new Alarm();
            alarm5.setReceiver(receiver);
            alarm5.setSender(sender);
            alarm5.setPostIdx(1);
            //alarm1.setMatchingIdx(0);
            alarm5.setAlarmType(AlarmType.OPENCHAT);
            alarm5.setContent("더미 알림 5");
            alarm5.setUrl("더미 URL 5");
            alarm5.setReaded(false);
            alarm5.setHistory("N");


            Alarm alarm6 = new Alarm();
            alarm6.setReceiver(receiver);
            alarm6.setSender(sender);
            alarm6.setPostIdx(1);
            alarm6.setMatchingIdx(0);
            alarm6.setAlarmType(AlarmType.ACCEPT);
            alarm6.setContent("더미 알림 6");
            alarm6.setUrl("더미 URL 6");
            alarm6.setReaded(false);
            alarm6.setHistory("N");

            Alarm alarm7 = new Alarm();
            alarm7.setReceiver(receiver);
            alarm7.setSender(sender);
            alarm7.setPostIdx(1);
            alarm7.setMatchingIdx(0);
            alarm7.setAlarmType(AlarmType.REFUSE);
            alarm7.setContent("더미 알림 7");
            alarm7.setUrl("더미 URL 7");
            alarm7.setReaded(false);
            alarm7.setHistory("N");

            Alarm alarm8 = new Alarm();
            alarm8.setReceiver(receiver);
            alarm8.setSender(sender);
            alarm8.setPostIdx(1);
            alarm8.setMatchingIdx(0);
            alarm8.setAlarmType(AlarmType.REPORT);
            alarm8.setContent("더미 알림 8");
            alarm8.setUrl("더미 URL 8");
            alarm8.setReaded(false);
            alarm8.setHistory("N");

            Alarm alarm9 = new Alarm();
            alarm9.setReceiver(receiver);
            alarm9.setSender(sender);
            //alarm1.setPostIdx(1);
            //alarm1.setMatchingIdx(0);
            alarm9.setAlarmType(AlarmType.EVENT);
            alarm9.setContent("더미 알림 9");
            alarm9.setUrl("더미 URL 9");
            alarm9.setReaded(false);
            alarm9.setHistory("N");

            Alarm alarm10 = new Alarm();
            alarm10.setReceiver(receiver);
            alarm10.setSender(sender);
            alarm10.setPostIdx(1);
            //alarm1.setMatchingIdx(0);
            alarm10.setAlarmType(AlarmType.REMIND);
            alarm10.setContent("더미 알림 10");
            alarm10.setUrl("더미 URL 10");
            alarm10.setReaded(false);
            alarm10.setHistory("N");

            alarmRepository.save(alarm1);
            alarmRepository.save(alarm2);

            alarmRepository.save(alarm3);

            alarmRepository.save(alarm4);
            alarmRepository.save(alarm5);
            alarmRepository.save(alarm6);
            alarmRepository.save(alarm7);
            alarmRepository.save(alarm8);
            alarmRepository.save(alarm9);
            alarmRepository.save(alarm10);

        }
    }

}
