package com.hibit2.hibit2.comment.repository;

import com.hibit2.hibit2.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByPostIdxAndParentCommentIsNullOrderByCreatedDate(int postId);

}
