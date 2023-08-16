package com.hibit2.hibit2.matching.repository;

import com.hibit2.hibit2.matching.domain.MatchStatus;
import com.hibit2.hibit2.matching.domain.Matching;
import com.hibit2.hibit2.member.domain.Member;
import com.hibit2.hibit2.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchingRepository extends JpaRepository<Matching, Integer>{
    List<Matching> findByPost(Post post);
    Matching findByMemberAndPost(Member member, Post post);
    List<Matching> findByPostIdxAndStatus(int postIdx, MatchStatus status);


}
