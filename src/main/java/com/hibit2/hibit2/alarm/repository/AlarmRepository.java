package com.hibit2.hibit2.alarm.repository;

import com.hibit2.hibit2.alarm.domain.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlarmRepository extends JpaRepository<Alarm, Integer> {
    List<Alarm> findByUserIdx(int user_idx);
}
