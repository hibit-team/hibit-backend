package com.hibit2.hibit2.alarm.repository;

import com.hibit2.hibit2.alarm.domain.Alarm;
import com.hibit2.hibit2.alarm.domain.AlarmType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlarmRepository extends JpaRepository<Alarm, Integer> {
    List<Alarm> findByReceiverIdAndSenderIdAndAlarmTypeAndHistory(Long receiver_idx, Long sender_idx, AlarmType alarmType,  String history);

    List<Alarm> findByReceiverId(Long member_idx);
}
