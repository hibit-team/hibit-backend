package com.hibit2.hibit2.repository;

import com.hibit2.hibit2.domain.Comment;
import com.hibit2.hibit2.domain.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostImageRepository extends JpaRepository<PostImage, Integer> {
}
