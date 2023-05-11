package com.hibit2.hibit2.repository;

import com.hibit2.hibit2.domain.Matching;
import com.hibit2.hibit2.domain.Post;
import com.hibit2.hibit2.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchingRepository extends JpaRepository<Matching, Integer>{
    List<Matching> findByPost(Post post);
    Matching findByUserAndPost(Users user, Post post);
}
