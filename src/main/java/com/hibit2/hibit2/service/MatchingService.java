package com.hibit2.hibit2.service;

import com.hibit2.hibit2.domain.MatchStatus;
import com.hibit2.hibit2.domain.Matching;
import com.hibit2.hibit2.domain.Post;
import com.hibit2.hibit2.domain.Users;
import com.hibit2.hibit2.repository.MatchingRepository;
import com.hibit2.hibit2.repository.PostRepository;
import com.hibit2.hibit2.repository.UsersRepository;
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

    //매칭 신청 유저 확인
    public boolean exitMatching(Users user, Post post) {
        Matching existing = matchingRepository.findByUserAndPost(user, post);
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

        for (String userId : userIds) {
            Users user = usersRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));
            Matching matchRequest = matchingRepository.findByUserAndPost(user, post);
            if (matchRequest == null) {
                throw new RuntimeException("매칭 요청을 찾을 수 없습니다.");
            }

            if (matchRequest.getStatus() == MatchStatus.HOLDING){
                matchRequest.setStatus(MatchStatus.PENDING);
            }
            else{
                Matching newmatching = new Matching();
                newmatching.setUser(user);
                newmatching.setPost(post);
                newmatching.setStatus(MatchStatus.PENDING);
                matchingRepository.save(newmatching);
            }
        }
    }
    //매칭 수락 (알림에서 수락 누른 경우)
    public void okMatch(int matching_idx) {
        Matching matching = matchingRepository.findById(matching_idx)
                .orElseThrow(() -> new RuntimeException("매칭 신청을 찾을 수 없습니다."));
        matching.setStatus(MatchStatus.OK);
        matchingRepository.save(matching);
    }
    //매칭 수락 (알림에서 거절 누른 경우) getMatchUserByPost
    public void noMatch(int matching_idx) {
        Matching matching = matchingRepository.findById(matching_idx)
                .orElseThrow(() -> new RuntimeException("매칭 신청을 찾을 수 없습니다."));
        matching.setStatus(MatchStatus.NO);
        matchingRepository.save(matching);
    }

    public List<String> getMatchUserByPost(int post_idx) {
        List<Matching> matchingList = matchingRepository.findByPostIdxAndStatus(post_idx, MatchStatus.OK);
        List<String> matchedUsers = new ArrayList<>();
        for (Matching matching : matchingList) {
            matchedUsers.add(matching.getUser().getId());
        }
        return matchedUsers;
    }



}
