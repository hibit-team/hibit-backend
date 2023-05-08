package com.hibit2.hibit2.service;

import com.hibit2.hibit2.domain.Comment;
import com.hibit2.hibit2.domain.Post;
import com.hibit2.hibit2.repository.CommentRepository;
import com.hibit2.hibit2.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    // 댓글 작성
    public Comment createComment(int post_idx, String content) {
        Post post = postRepository.findById(post_idx)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        Comment comment = new Comment();
        comment.setPost(post);
        comment.setContent(content);
        return commentRepository.save(comment);
    }

    // 대댓글 작성
    public Comment createReply(int comment_idx, String content) {
        Comment parentComment = commentRepository.findById(comment_idx)
                .orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다."));
        Comment reply = new Comment();
        reply.setParentComment(parentComment);
        reply.setContent(content);
        reply.setPost(parentComment.getPost());
        parentComment.addChildComment(reply);
        return commentRepository.save(reply);
    }

    // 댓글 조회 -> post idx에 해당하는 모든 댓글 리스트
    public List<Comment> getCommentsByPost(int post_idx) {
        return commentRepository.findByPostIdxAndParentCommentIsNullOrderByCreatedDate(post_idx);
    }

    // 댓글 수정
    public Comment updateComment(int comment_idx, String newContent) {
        Comment comment = commentRepository.findById(comment_idx)
                .orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다."));
        comment.setContent(newContent);
        return commentRepository.save(comment);
    }

    // 댓글 삭제
    public void deleteComment(int comment_idx) {
        Comment comment = commentRepository.findById(comment_idx)
                .orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다."));
        commentRepository.delete(comment);
    }
}
