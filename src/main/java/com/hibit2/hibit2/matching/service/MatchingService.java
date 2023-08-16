package com.hibit2.hibit2.matching.service;


import com.hibit2.hibit2.alarm.domain.Alarm;
import com.hibit2.hibit2.alarm.domain.AlarmType;
import com.hibit2.hibit2.alarm.repository.AlarmRepository;
import com.hibit2.hibit2.alarm.service.AlarmService;
import com.hibit2.hibit2.mail.service.EmailService;
import com.hibit2.hibit2.matching.domain.MatchStatus;
import com.hibit2.hibit2.matching.domain.Matching;
import com.hibit2.hibit2.matching.repository.MatchingRepository;
import com.hibit2.hibit2.member.domain.Member;
import com.hibit2.hibit2.member.repository.MemberRepository;
import com.hibit2.hibit2.post.domain.Post;
import com.hibit2.hibit2.post.repository.PostRepository;
import com.hibit2.hibit2.postHistory.domain.postHistory;
import com.hibit2.hibit2.postHistory.repository.postHistoryRepository;
import com.hibit2.hibit2.user.domain.Users;
import com.hibit2.hibit2.user.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchingService {
    private final MatchingRepository matchingRepository;
    private final PostRepository postRepository;
    private final UsersRepository usersRepository;
    private final AlarmService alarmService;
    private final EmailService emailService;
    private final postHistoryRepository postHistoryRepository;
    private final AlarmRepository alarmRepository;
    private final MemberRepository memberRepository;

    //매칭 신청 유저 확인
    public boolean exitMatching(Member member, Post post) {
        Matching existing = matchingRepository.findByMemberAndPost(member, post);
        return existing != null;
    }

    //매칭 신청자 리스트
    public List<Matching> getMatchRequestsByPost(int post_idx) {
        Post post = postRepository.findById(post_idx)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        return matchingRepository.findByPost(post);
    }
    //초대장 발송
    @Transactional
    public void sendInvitations(int postIdx, List<String> userIds) {
        Post post = postRepository.findById(postIdx)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        post.increaseRound();
        for (String userId : userIds) {
            Member member= memberRepository.findByNickname(userId)
                    .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

            Matching matchRequest = matchingRepository.findByMemberAndPost(member, post);
            if (matchRequest == null) {
                throw new RuntimeException("매칭 요청을 찾을 수 없습니다.");
            }
            //댓글을 달고 처음 초대하는 경우
            if (matchRequest.getStatus() == MatchStatus.HOLDING)
            {
                matchRequest.setStatus(MatchStatus.PENDING);
                matchRequest.setRound(post.getRound());
                //알람 생성
                //Alarm alarm = alarmService.createAlarm(user ,post.getUser(), post.getIdx(), matchRequest.getId(), AlarmType.INVITATION, "");
                //초대장 발송 이메일 발송
                //emailService.mailSend(user, "[히빗] 초대장이 도착했습니다.", alarm.getContent() + "\nhttps://hibit.shop");
            }
            //한 번 이상 보냈을 경우
            else{
                Matching newmatching = new Matching();
                newmatching.setMember(member);
                newmatching.setPost(post);
                newmatching.setStatus(MatchStatus.PENDING);
                newmatching.setRound(post.getRound());
                matchingRepository.save(newmatching);
                //알람 생성
                //Alarm alarm = alarmService.createAlarm(user ,post.getUser(), post.getIdx(), newmatching.getId(), AlarmType.INVITATION, "");

                //초대장 발송 이메일 발송
                //emailService.mailSend(user, "[히빗] 초대장이 도착했습니다.", alarm.getContent() + "\nhttps://hibit.shop");
            }
        }
    }

    //매칭 수락 (알림에서 수락 누른 경우)
    public void okMatch(int matching_idx) {
        Matching matching = matchingRepository.findById(matching_idx)
                .orElseThrow(() -> new RuntimeException("매칭 신청을 찾을 수 없습니다."));
        matching.setStatus(MatchStatus.OK);
        postHistory postHistory = postHistoryRepository.findByPostIdx(matching.getPost().getIdx());
        postHistory.increaseOk();
        postHistoryRepository.save(postHistory);
        matchingRepository.save(matching);

        //알람 생성 (옾챗링크
        String url = matching.getPost().getOpenchat();
        //alarmService.createAlarm(matching.getUser() ,matching.getPost().getUser(),matching.getPost().getIdx(), matching_idx, AlarmType.OPENCHAT, url);
        //알림 생성(수락)
        //Alarm alarm = alarmService.createAlarm(matching.getPost().getUser(), matching.getUser() ,matching.getPost().getIdx(), matching_idx, AlarmType.ACCEPT, "");

        //초대장 알림 history 추가
        //List<Alarm> yetStatusAlarms = alarmRepository.findByUserIdxAndSenderIdxAndAlarmTypeAndHistory(matching.getUser().getIdx(), matching.getPost().getUser().getIdx(), AlarmType.INVITATION, "YET");
        //yetStatusAlarms.forEach(yetAlarm -> {
        //    yetAlarm.setHistory("OK");
       // });

        //alarmRepository.saveAll(yetStatusAlarms);

        //초대장 발송 이메일 발송
        //emailService.mailSend(matching.getPost().getUser(), "[히빗] 초대가 수락되었습니다.", alarm.getContent() + "\nhttps://hibit.shop");

    }
    //매칭 거절 (알림에서 거절 누른 경우)
    public void noMatch(int matching_idx) {
        Matching matching = matchingRepository.findById(matching_idx)
                .orElseThrow(() -> new RuntimeException("매칭 신청을 찾을 수 없습니다."));
        matching.setStatus(MatchStatus.NO);
        postHistory postHistory = postHistoryRepository.findByPostIdx(matching.getPost().getIdx());
        postHistory.increaseNo();
        postHistoryRepository.save(postHistory);
        matchingRepository.save(matching);

        //알림 생성
        //Alarm alarm = alarmService.createAlarm(matching.getPost().getUser(), matching.getUser() ,matching.getPost().getIdx(), matching_idx, AlarmType.REFUSE, "");

        //초대장 알림 history 추가
        //List<Alarm> yetStatusAlarms = alarmRepository.findByUserIdxAndSenderIdxAndAlarmTypeAndHistory(matching.getUser().getIdx(), matching.getPost().getUser().getIdx(), AlarmType.INVITATION, "YET");
        //yetStatusAlarms.forEach(yetAlarm -> {
        //    yetAlarm.setHistory("NO");
        //});


        //초대장 발송 이메일 발송
        //emailService.mailSend(matching.getPost().getUser(), "[히빗] 초대가 거절되었습니다.", alarm.getContent() + "\nhttps://hibit.shop");


    }

    //수락 유저 리스트
    public List<String> getMatchUserByPost(int post_idx) {
        List<Matching> matchingList = matchingRepository.findByPostIdxAndStatus(post_idx, MatchStatus.OK);
        List<String> matchedUsers = new ArrayList<>();
        for (Matching matching : matchingList) {
            String userId = matching.getMember().getNickname();
            if (!matchedUsers.contains(userId)) {
                matchedUsers.add(userId);
            }
        }
        return matchedUsers;
    }

    //수락한 유저 중, 진짜 간 유저들
    @Transactional
    public void saveOkuser(int postIdx, List<String> userIds) {
        Post post = postRepository.findById(postIdx)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        postHistory postHistory = postHistoryRepository.findByPostIdx(postIdx);

        // 기존의 okUsers 리스트에 userIds를 추가
        postHistory.getRealUsers().addAll(userIds);

        // postHistory 엔티티를 저장
        postHistoryRepository.save(postHistory);

        /*
        for (String userId : userIds) {
            Users user = usersRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));
        }
        */
    }


}
