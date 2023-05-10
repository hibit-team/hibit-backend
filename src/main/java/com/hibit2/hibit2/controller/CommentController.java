package com.hibit2.hibit2.controller;

import com.hibit2.hibit2.domain.Comment;
import com.hibit2.hibit2.service.CommentService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // 댓글 작성
    @PostMapping("/{post_idx}")
    public ResponseEntity<Comment> createComment(@PathVariable int post_idx, @RequestBody String content) {
        Comment comment = commentService.createComment(post_idx, content);
        return ResponseEntity.status(HttpStatus.CREATED).body(comment);
    }

    // 대댓글 작성
    @PostMapping("/replies/{comment_idx}")
    public ResponseEntity<Comment> createReply(@PathVariable int comment_idx, @RequestBody String content) {
        Comment reply = commentService.createReply(comment_idx, content);
        return ResponseEntity.status(HttpStatus.CREATED).body(reply);
    }

    // 댓글 조회
    @GetMapping("/list/{post_idx}")
    public ResponseEntity<List<Comment>> getCommentsByPost(@PathVariable int post_idx) {
        List<Comment> comments = commentService.getCommentsByPost(post_idx);
        comments.forEach(this::initializeChildComments);
        return ResponseEntity.ok(comments);
    }
    private void initializeChildComments(Comment comment) {
        Hibernate.initialize(comment.getChildComments());
        comment.getChildComments().forEach(this::initializeChildComments);
    }

    // 댓글 수정
    @PutMapping("/update/{comment_idx}")
    public ResponseEntity<Comment> updateComment(@PathVariable int comment_idx, @RequestBody String newContent) {
        Comment updatedComment = commentService.updateComment(comment_idx, newContent);
        return ResponseEntity.ok(updatedComment);
    }

    // 댓글 삭제
    @DeleteMapping("/delete/{comment_idx}")
    public ResponseEntity<Void> deleteComment(@PathVariable int comment_idx) {
        commentService.deleteComment(comment_idx);
        return ResponseEntity.noContent().build();
    }

    //댓글 좋아요
    @GetMapping("/like/{comment_idx}")
    public ResponseEntity<Comment> likeComment(@PathVariable int comment_idx){
        String user_id = "b"; //나중에는 현재 로그인한 유저의 id 찾아오기
        Comment comment = commentService.likeComment(comment_idx, user_id);
        return ResponseEntity.ok(comment);
    }


}
