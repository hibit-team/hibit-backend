package com.hibit2.hibit2.alarm.controller;


import com.hibit2.hibit2.alarm.domain.Alarm;
import com.hibit2.hibit2.alarm.domain.AlarmType;
import com.hibit2.hibit2.alarm.dto.AlarmListDto;
import com.hibit2.hibit2.alarm.repository.AlarmRepository;
import com.hibit2.hibit2.alarm.service.AlarmService;
import com.hibit2.hibit2.user.domain.Users;
import com.hibit2.hibit2.user.repository.UsersRepository;
import io.swagger.v3.oas.annotations.Operation;
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
    private final UsersRepository usersRepository;
    private final AlarmRepository alarmRepository;

    @PostMapping("/make")
    @Operation(summary = "alarm/make", description = "모든 유저에게 알림 생성(이벤트)")
    public ResponseEntity<Alarm> save(@RequestBody String content){

        //추후 관리자 아이디로 변경
        Users sender = usersRepository.findById(1)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        List<Users> allUsers = usersRepository.findAll();

        for (Users user : allUsers) {
            Alarm alarm = new Alarm();
            alarm.setUser(user);
            alarm.setSender(sender);
            alarm.setAlarmType(AlarmType.EVENT);
            alarm.setUrl("");
            alarm.setContent(content);
            alarmRepository.save(alarm);
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @GetMapping("/list/{user_idx}")
    @Operation(summary = "alarm/list/{user_idx}", description = "알람 전체 리스트")
    public ResponseEntity<List<AlarmListDto>> getAlarmList(@PathVariable int user_idx){
        List<Alarm> alarms = alarmService.getAlarmByUserIdx(user_idx);
        alarms.sort(Comparator.comparing(Alarm::getCreatedDate).reversed());
        List<AlarmListDto> alarmListDtos = new ArrayList<>();
        for (Alarm alarm : alarms){
            AlarmListDto alarmListDto = new AlarmListDto(alarm);
            alarmListDtos.add(alarmListDto);
            alarm.readAlarm();
            alarmRepository.save(alarm);
        }
        return ResponseEntity.ok(alarmListDtos);
    }

}
