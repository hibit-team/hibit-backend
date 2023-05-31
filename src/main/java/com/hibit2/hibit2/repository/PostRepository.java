package com.hibit2.hibit2.repository;

import com.hibit2.hibit2.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findALlByDeleteYn(char deleteYn, Sort sort);
    Page<Post> findByDeleteYn(char flag, Pageable pageable);

}
