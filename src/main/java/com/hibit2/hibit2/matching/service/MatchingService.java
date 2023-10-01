package com.hibit2.hibit2.matching.service;


import com.hibit2.hibit2.alarm.domain.Alarm;
import com.hibit2.hibit2.alarm.domain.AlarmType;
import com.hibit2.hibit2.alarm.repository.AlarmRepository;
import com.hibit2.hibit2.alarm.service.AlarmService;
import com.hibit2.hibit2.matching.domain.MatchStatus;
import com.hibit2.hibit2.matching.domain.Matching;
import com.hibit2.hibit2.matching.exception.NotFoundMatchingException;
import com.hibit2.hibit2.matching.repository.MatchingRepository;
import com.hibit2.hibit2.member.domain.Member;
import com.hibit2.hibit2.member.repository.MemberRepository;
import com.hibit2.hibit2.post.domain.Post;
import com.hibit2.hibit2.post.exception.NotFoundPostException;
import com.hibit2.hibit2.post.repository.PostRepository;
import com.hibit2.hibit2.postHistory.domain.postHistory;
import com.hibit2.hibit2.postHistory.repository.postHistoryRepository;
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
    private final AlarmService alarmService;
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
                .orElseThrow(() -> new NotFoundPostException());
        return matchingRepository.findByPost(post);
    }
    //초대장 발송
    @Transactional
    public void sendInvitations(int postIdx, List<Long> memberIds) {
        Post post = postRepository.findById(postIdx)
                .orElseThrow(() -> new NotFoundPostException());
        post.increaseRound();
        for (Long memberId : memberIds) {
            Member member= memberRepository.getById(memberId);

            Matching matchRequest = matchingRepository.findByMemberAndPost(member, post);
            if (matchRequest == null) {
                throw new NotFoundMatchingException();
            }
            //댓글을 달고 처음 초대하는 경우
            if (matchRequest.getStatus() == MatchStatus.HOLDING)
            {
                matchRequest.setStatus(MatchStatus.PENDING);
                matchRequest.setRound(post.getRound());
                //알람 생성
                alarmService.createAlarm(member ,post.getMember(), post.getIdx(), matchRequest.getId(), AlarmType.INVITATION, "");
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
                alarmService.createAlarm(member ,post.getMember(), post.getIdx(), newmatching.getId(), AlarmType.INVITATION, "");

                //초대장 발송 이메일 발송
                //emailService.mailSend(user, "[히빗] 초대장이 도착했습니다.", alarm.getContent() + "\nhttps://hibit.shop");
            }
        }
    }

    //매칭 수락 (알림에서 수락 누른 경우)
    public void okMatch(int matching_idx) {
        Matching matching = matchingRepository.findById(matching_idx)
                .orElseThrow(() -> new NotFoundMatchingException());
        matching.setStatus(MatchStatus.OK);
        postHistory postHistory = postHistoryRepository.findByPostIdx(matching.getPost().getIdx());
        postHistory.increaseOk();
        postHistoryRepository.save(postHistory);
        matchingRepository.save(matching);

        //알람 생성 (옾챗링크
        String url = matching.getPost().getOpenchat();
        alarmService.createAlarm(matching.getMember() ,matching.getPost().getMember(),matching.getPost().getIdx(), matching_idx, AlarmType.OPENCHAT, url);
        //알림 생성(수락)
        alarmService.createAlarm(matching.getPost().getMember(), matching.getMember() ,matching.getPost().getIdx(), matching_idx, AlarmType.ACCEPT, "");

        //초대장 알림 history 추가
        List<Alarm> yetStatusAlarms = alarmRepository.findByReceiverIdAndSenderIdAndAlarmTypeAndHistory(matching.getMember().getId(), matching.getPost().getMember().getId(), AlarmType.INVITATION, "YET");
        yetStatusAlarms.forEach(yetAlarm -> {
            yetAlarm.setHistory("OK");
        });

        alarmRepository.saveAll(yetStatusAlarms);

        //초대장 발송 이메일 발송
        //emailService.mailSend(matching.getPost().getUser(), "[히빗] 초대가 수락되었습니다.", alarm.getContent() + "\nhttps://hibit.shop");

    }
    //매칭 거절 (알림에서 거절 누른 경우)
    public void noMatch(int matching_idx) {
        Matching matching = matchingRepository.findById(matching_idx)
                .orElseThrow(() -> new NotFoundMatchingException());
        matching.setStatus(MatchStatus.NO);
        postHistory postHistory = postHistoryRepository.findByPostIdx(matching.getPost().getIdx());
        postHistory.increaseNo();
        postHistoryRepository.save(postHistory);
        matchingRepository.save(matching);

        //알림 생성
        alarmService.createAlarm(matching.getPost().getMember(), matching.getMember() ,matching.getPost().getIdx(), matching_idx, AlarmType.REFUSE, "");

        //초대장 알림 history 추가
        List<Alarm> yetStatusAlarms = alarmRepository.findByReceiverIdAndSenderIdAndAlarmTypeAndHistory(matching.getMember().getId(), matching.getPost().getMember().getId(), AlarmType.INVITATION, "YET");
        yetStatusAlarms.forEach(yetAlarm -> {
            yetAlarm.setHistory("NO");
        });

        //초대장 발송 이메일 발송
        //emailService.mailSend(matching.getPost().getUser(), "[히빗] 초대가 거절되었습니다.", alarm.getContent() + "\nhttps://hibit.shop");
    }

    //수락 유저 리스트
    public List<Member> getMatchUserByPost(int post_idx) {
        List<Matching> matchingList = matchingRepository.findByPostIdxAndStatus(post_idx, MatchStatus.OK);
        List<Member> matchedUsers = new ArrayList<>();
        for (Matching matching : matchingList) {
            Member member =  matching.getMember();
            if (!matchedUsers.contains(member)) {
                matchedUsers.add(member);
            }
        }
        return matchedUsers;
    }

    //수락한 유저 중, 진짜 간 유저들
    @Transactional
    public void saveOkuser(int postIdx, List<Long> memberIds) {
        Post post = postRepository.findById(postIdx)
                .orElseThrow(() -> new NotFoundPostException());
        postHistory postHistory = postHistoryRepository.findByPostIdx(postIdx);
        List<Member> realUsers = new ArrayList<>();

        for (Long id : memberIds){
            Member member= memberRepository.getById(id);
            if(!realUsers.contains(member)){
                realUsers.add(member);
            }
        }

        // 기존의 okUsers 리스트에 추가
        postHistory.getRealUsers().addAll(realUsers);

        // postHistory 엔티티를 저장
        postHistoryRepository.save(postHistory);


    }


}
