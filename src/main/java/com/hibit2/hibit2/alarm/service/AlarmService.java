package com.hibit2.hibit2.alarm.service;

import com.hibit2.hibit2.alarm.domain.Alarm;
import com.hibit2.hibit2.alarm.domain.AlarmType;
import com.hibit2.hibit2.alarm.repository.AlarmRepository;
import com.hibit2.hibit2.global.repository.MatchingRepository;
import com.hibit2.hibit2.post.repository.PostRepository;
import com.hibit2.hibit2.user.domain.Users;
import com.hibit2.hibit2.user.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlarmService {
    private final MatchingRepository matchingRepository;
    private final PostRepository postRepository;
    private final UsersRepository usersRepository;
    private final AlarmRepository alarmRepository;

    //알림 생성
    public Alarm createAlarm(Users user,  AlarmType alarmType, String url){
        String content;
        if (alarmType == AlarmType.COMMENT){
            content = user.getId()+ "님이 회원님의 게시글에 댓글을 남겼습니다.";
        } else if (alarmType == AlarmType.RECOMMENT) {
            content = user.getId() + "님이 회원님의 댓글에 대댓글을 남겼습니다.";
        } else if (alarmType == AlarmType.COMMENTHEART) {
            content = user.getId() + "님이 회원님의 댓글을 좋아합니다.";
        } else if (alarmType == AlarmType.INVITATION) {
            content = user.getId()+ "님이 회원님께 초대장을 전송했습니다.";
        } else if (alarmType == AlarmType.OPENCHAT) {
            content = user.getId()+ "님이 오픈채팅방 링크가 도착했습니다.";
        } else if (alarmType == AlarmType.ACCEPT){
            content = user.getId() + "님이 초대를 수락하셨어요.";
        } else if (alarmType==AlarmType.REFUSE){
            content = user.getId() + "님이 초대를 거절하셨어요.";
        } else if (alarmType==AlarmType.REPORT) {
            content = user.getId()+ "회원님의 계정이 서비스 이용 수칙을 위반하여 신고되었습니다.";
        } else if (alarmType==AlarmType.EVENT){
            //관리자가 변경해야 함
            content = "";
        } else {
            throw new IllegalArgumentException("알 수 없는 알림 타입입니다.");
        }
        Alarm alarm = new Alarm();
        alarm.setUser(user);
        alarm.setAlarmType(alarmType);
        alarm.setUrl(url);
        alarm.setContent(content);
        alarmRepository.save(alarm);
        return alarm;
    }

    //알림 보기
    public List<Alarm> getAlarmByUserIdx(int user_idx){
        return alarmRepository.findByUserIdx(user_idx);
    }

}
