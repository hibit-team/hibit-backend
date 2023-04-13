package com.hibit2.hibit2.repository;

import com.hibit2.hibit2.domain.Post;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findALlByDeleteYn(final char deleteYn, final Sort sort);

}
