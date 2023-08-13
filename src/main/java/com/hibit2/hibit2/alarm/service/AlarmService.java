package com.hibit2.hibit2.alarm.service;

import com.hibit2.hibit2.alarm.domain.Alarm;
import com.hibit2.hibit2.alarm.domain.AlarmType;
import com.hibit2.hibit2.alarm.repository.AlarmRepository;
import com.hibit2.hibit2.global.repository.MatchingRepository;
import com.hibit2.hibit2.matching.domain.Matching;
import com.hibit2.hibit2.post.domain.Post;
import com.hibit2.hibit2.post.repository.PostRepository;
import com.hibit2.hibit2.user.domain.Users;
import com.hibit2.hibit2.user.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlarmService {
    private final MatchingRepository matchingRepository;
    private final PostRepository postRepository;
    private final UsersRepository usersRepository;
    private final AlarmRepository alarmRepository;

    //알림 생성
    @Transactional
    public Alarm createAlarm(Users user, Users sender, int postIdx, int matchingIdx, AlarmType alarmType, String url){
        Alarm alarm = new Alarm();

        String content;
        if (alarmType == AlarmType.COMMENT){
            content = sender.getId()+ "님이 회원님의 게시글에 댓글을 남겼습니다.";
        } else if (alarmType == AlarmType.RECOMMENT) {
            content = sender.getId() + "님이 회원님의 댓글에 대댓글을 남겼습니다.";
        } else if (alarmType == AlarmType.COMMENTHEART) {
            content = sender.getId() + "님이 회원님의 댓글을 좋아합니다.";
        } else if (alarmType == AlarmType.INVITATION) {
            content = sender.getId()+ "님이 회원님께 초대장을 전송했습니다.";
            alarm.setHistory("YET");
        } else if (alarmType == AlarmType.OPENCHAT) {
            content = sender.getId()+ "님이 오픈채팅방 링크가 도착했습니다.";
        } else if (alarmType == AlarmType.ACCEPT){
            content = sender.getId() + "님이 초대를 수락하셨어요.";
        } else if (alarmType==AlarmType.REFUSE){
            content = sender.getId() + "님이 초대를 거절하셨어요.";
        } else if (alarmType==AlarmType.REPORT) {
            content = sender.getId()+ "회원님의 계정이 서비스 이용 수칙을 위반하여 신고되었습니다.";
        } else if (alarmType==AlarmType.EVENT){
            //관리자가 변경해야 함
            content = "";
        } else {
            throw new IllegalArgumentException("알 수 없는 알림 타입입니다.");
        }
        alarm.setUser(user);
        alarm.setSender(sender);
        alarm.setAlarmType(alarmType);
        alarm.setUrl(url);
        alarm.setContent(content);
        alarm.setPostIdx(postIdx);
        alarm.setMatchingIdx(matchingIdx);
        alarmRepository.save(alarm);
        return alarm;
    }

    //알림 보기
    public List<Alarm> getAlarmByUserIdx(int user_idx){
        return alarmRepository.findByUserIdx(user_idx);
    }


    //리마인더 알람 생성
    @Transactional
    public Alarm createRemind(Users user){

        //추후 관리자 아이디로 변경
        Users sender = usersRepository.findById("a")
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        String content;
        content = "전시회 다녀오셨나요? 추후 변경";
        Alarm alarm = new Alarm();
        alarm.setUser(user);
        alarm.setSender(sender);
        alarm.setAlarmType(AlarmType.REMIND);
        alarm.setUrl("");
        alarm.setContent(content);
        alarmRepository.save(alarm);
        return alarm;
    }

}
