package com.hibit2.hibit2.service;

import com.hibit2.hibit2.domain.*;
import com.hibit2.hibit2.repository.CommentRepository;
import com.hibit2.hibit2.repository.MatchingRepository;
import com.hibit2.hibit2.repository.PostRepository;
import com.hibit2.hibit2.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UsersRepository usersRepository;
    private final MatchingRepository matchingRepository;
    private final MatchingService matchingService;
    // 댓글 작성
    public Comment createComment(int post_idx, int user_idx, String content) {
        Post post = postRepository.findById(post_idx)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        //추후 이 부분 삭제하고, 로그인 한 유저로 변경
        Users user = usersRepository.findById(user_idx)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setContent(content);

        post.increaseCommentNumber();
        //매칭 신청여부 확인, 추후 자신이 쓴 글에는 매칭 신청 불가능하도록 변경
        if (!matchingService.exitMatching(user, post)) {
            Matching matching = new Matching(user, post);
            matchingRepository.save(matching);
        }

        return commentRepository.save(comment);
    }

    // 대댓글 작성
    public Comment createReply(int comment_idx, int user_idx, String content) {
        Comment parentComment = commentRepository.findById(comment_idx)
                .orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다."));
        Post post = postRepository.findById(parentComment.getPost().getIdx())
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        Users user = usersRepository.findById(user_idx)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        Comment reply = new Comment();
        reply.setParentComment(parentComment);
        reply.setContent(content);
        reply.setPost(parentComment.getPost());
        parentComment.addChildComment(reply);
        post.increaseCommentNumber();

        //매칭 신청여부 확인
        if (!matchingService.exitMatching(user, post)) {
            Matching matching = new Matching(user, post);
            matchingRepository.save(matching);
        }

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
        if (comment.getParentComment() != null) {
            // 대댓글 삭제 -> -1
            Comment parentComment = comment.getParentComment();
            Post post = parentComment.getPost();
            int count =1;
            post.decreaseCommentNumber(count);
        } else {
            // 댓글 삭제 -> 대댓글 수도 같이 -
            Post post = comment.getPost();

            int count = 1;
            List<Comment> childComments = comment.getChildComments();
            for (Comment childComment : childComments) {
                count++;
            }
            post.decreaseCommentNumber(count);
        }


        commentRepository.delete(comment);
    }
    public Comment likeComment(int comment_idx, String userId){
        Comment comment = commentRepository.findById(comment_idx)
                .orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다."));
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        // 사용자가 이미 좋아요를 눌렀는지 확인
        Optional<Users> existingLike = comment.getLikeUsers().stream()
                .filter(likeUser -> likeUser.getId().equals(userId))
                .findFirst();

        if (!existingLike.isPresent()) {
            // 좋아요 추가
            comment.getLikeUsers().add(user);
            comment.increaseLike();
        } else {
            // 좋아요 취소
            comment.getLikeUsers().remove(existingLike.get());
            comment.decreaseLike();
        }
        return commentRepository.save(comment);
    }



}
