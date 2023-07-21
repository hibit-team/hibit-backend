package com.hibit2.hibit2.alarm.controller;


import com.hibit2.hibit2.alarm.domain.Alarm;
import com.hibit2.hibit2.alarm.service.AlarmService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "alarm", description = "알람")
@RestController
@RequiredArgsConstructor

@RequestMapping("/alarm")
public class AlarmController {
    private final AlarmService alarmService;

    @GetMapping("/list/{user_idx}")
    @Operation(summary = "alarm/list/{user_idx}", description = "알람 전체 리스트")
    public ResponseEntity<List<Alarm>> getAlarmList(@PathVariable int user_idx){
        List<Alarm> alarms = alarmService.getAlarmByUserIdx(user_idx);
        return ResponseEntity.ok(alarms);
    }

}
