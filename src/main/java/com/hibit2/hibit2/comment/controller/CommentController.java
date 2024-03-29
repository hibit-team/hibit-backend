package com.hibit2.hibit2.comment.controller;


import com.hibit2.hibit2.auth.dto.LoginMember;
import com.hibit2.hibit2.auth.presentation.AuthenticationPrincipal;
import com.hibit2.hibit2.comment.domain.Comment;
import com.hibit2.hibit2.comment.dto.CommentListDto;
import com.hibit2.hibit2.comment.dto.CommentSaveDto;
import com.hibit2.hibit2.comment.repository.CommentRepository;
import com.hibit2.hibit2.comment.service.CommentService;
import com.hibit2.hibit2.member.domain.Member;
import com.hibit2.hibit2.member.repository.MemberRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@Tag(name = "comment", description = "댓글 및 대댓글")

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor

public class CommentController {

    private final CommentService commentService;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    @PostMapping("/{post_idx}")
    @Operation(summary = "/comment/1", description = "댓글 작성")
    public ResponseEntity<Integer> createComment(@Parameter(hidden = true) @AuthenticationPrincipal final LoginMember loginMember, @PathVariable int post_idx, @RequestBody CommentSaveDto commentSaveDto) {
        String content = commentSaveDto.getContent();
        Comment comment = commentService.createComment(post_idx, loginMember.getId(), content);
        return ResponseEntity.status(HttpStatus.CREATED).body(comment.getIdx());
    }

    // 대댓글 작성
    @PostMapping("/replies/{comment_idx}")
    @Operation(summary = "/comment/replies/1", description = "댓글에 대한 대댓글 작성")
    public ResponseEntity<Integer> createReply(@Parameter(hidden = true) @AuthenticationPrincipal final LoginMember loginMember, @PathVariable int comment_idx, @RequestBody CommentSaveDto commentSaveDto) {
        String content = commentSaveDto.getContent();
        Comment reply = commentService.createReply(comment_idx, loginMember.getId(), content);
        return ResponseEntity.status(HttpStatus.CREATED).body(reply.getIdx());
    }

    // 댓글 조회
    @GetMapping("/list/{post_idx}")
    @Operation(summary = "/comment/list/1", description = "게시글에 대한 전체 리스트")
    public ResponseEntity<List<CommentListDto>> getCommentsByPost(@PathVariable int post_idx) {
        List<Comment> comments = commentService.getCommentsByPost(post_idx);
        List<CommentListDto> commentListDtos = new ArrayList<>();

        for (Comment comment : comments) {
            CommentListDto commentListDto = new CommentListDto(comment);
            commentListDtos.add(commentListDto);
        }
        return ResponseEntity.ok(commentListDtos);
    }


    // 댓글 수정
    @PutMapping("/update/{comment_idx}")
    @Operation(summary = "/comment/update/1", description = "댓글 수정")
    public ResponseEntity<Comment> updateComment(@Parameter(hidden = true) @AuthenticationPrincipal final LoginMember loginMember, @PathVariable int comment_idx, @RequestBody CommentSaveDto commentSaveDto) {
        Comment comment = commentRepository.findById(comment_idx).orElseThrow(()-> new IllegalArgumentException("해당 댓글이 없습니다. id="+comment_idx));;
        if (loginMember.getId() == comment.getMember().getId()) {
            String content = commentSaveDto.getContent();
            Comment updatedComment = commentService.updateComment(comment_idx, content);
            return ResponseEntity.ok(updatedComment);
        }
        else{
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    // 댓글 삭제
    @DeleteMapping("/delete/{comment_idx}")
    @Operation(summary = "/comment/delete/1", description = "댓글 삭제")
    public ResponseEntity<Void> deleteComment(@Parameter(hidden = true) @AuthenticationPrincipal final LoginMember loginMember,@PathVariable int comment_idx) {
        Comment comment = commentRepository.findById(comment_idx).orElseThrow(()-> new IllegalArgumentException("해당 댓글이 없습니다. id="+comment_idx));;
        if (loginMember.getId() == comment.getMember().getId()) {
            commentService.deleteComment(comment_idx);
            return ResponseEntity.noContent().build();
        }
        else{
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
    //댓글 좋아요
    @GetMapping("/like/{comment_idx}")
    @Operation(summary = "/comment/like/1", description = "댓글 좋아요")
    public ResponseEntity<Comment> likeComment(@Parameter(hidden = true) @AuthenticationPrincipal final LoginMember loginMember, @PathVariable int comment_idx){

        Member member= memberRepository.getById(loginMember.getId());
        Comment comment = commentService.likeComment(comment_idx, member.getNickname());
        return ResponseEntity.ok(comment);
    }
}
