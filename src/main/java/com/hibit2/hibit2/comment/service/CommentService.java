package com.hibit2.hibit2.comment.service;


import com.hibit2.hibit2.alarm.domain.AlarmType;
import com.hibit2.hibit2.alarm.service.AlarmService;
import com.hibit2.hibit2.comment.domain.Comment;
import com.hibit2.hibit2.comment.repository.CommentRepository;
import com.hibit2.hibit2.matching.domain.Matching;
import com.hibit2.hibit2.matching.repository.MatchingRepository;
import com.hibit2.hibit2.matching.service.MatchingService;
import com.hibit2.hibit2.member.domain.Member;
import com.hibit2.hibit2.member.repository.MemberRepository;
import com.hibit2.hibit2.post.domain.Post;


import com.hibit2.hibit2.post.repository.PostRepository;
import com.hibit2.hibit2.user.domain.Users;
import com.hibit2.hibit2.user.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final MatchingRepository matchingRepository;
    private final MatchingService matchingService;
    private final AlarmService alarmService;
    private final MemberRepository memberRepository;
    // 댓글 작성
    @Transactional
    public Comment createComment(int post_idx, Long member_idx, String content) {
        Post post = postRepository.findById(post_idx)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        Member member= memberRepository.getById(member_idx);

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setMember(member);
        comment.setContent(content);
        //댓글을 처음 단 상황인지 파악, 자신이 쓴 글은 매칭 추가 안함
        if (!matchingService.exitMatching(member, post) && member_idx != post.getMember().getId()) {
            Matching matching = new Matching(member, post);
            matchingRepository.save(matching);
            //알림 생성
            alarmService.createAlarm(post.getMember(), member, post.getIdx(),matching.getId(), AlarmType.COMMENT, "");
        }
        return commentRepository.save(comment);
    }

    // 대댓글 작성
    @Transactional
    public Comment createReply(int comment_idx, Long member_idx, String content) {
        Comment parentComment = commentRepository.findById(comment_idx)
                .orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다."));
        Post post = postRepository.findById(parentComment.getPost().getIdx())
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        Member member= memberRepository.getById(member_idx);


        Comment reply = new Comment();
        reply.setParentComment(parentComment);
        reply.setMember(member);
        reply.setContent(content);
        reply.setPost(parentComment.getPost());
        parentComment.addChildComment(reply);
        post.increaseCommentNumber();

        //매칭 신청여부 확인
        if (!matchingService.exitMatching(member, post) && member_idx != post.getMember().getId()) {
            Matching matching = new Matching(member, post);
            matchingRepository.save(matching);

            //알람 생성 (게시글작성자 -> 댓글 알람, 댓글 작성자 -> 대댓글 알람)
            alarmService.createAlarm(post.getMember(), member, post.getIdx(), matching.getId(),AlarmType.COMMENT, "");
            alarmService.createAlarm(parentComment.getMember(), member, post.getIdx(), matching.getId(), AlarmType.RECOMMENT, "");
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
    //댓글 좋아요
    public Comment likeComment(int comment_idx, String nickname){
        Comment comment = commentRepository.findById(comment_idx)
                .orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다."));
        Member member= memberRepository.findByNickname(nickname)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        Optional<Member> existingLike = comment.getLikeUsers().stream()
                .filter(likeUser -> likeUser.getId().equals(member))
                .findFirst();

        if (!existingLike.isPresent()) {
            // 좋아요 추가
            comment.getLikeUsers().add(member);
            comment.increaseLike();
            //알람 생성
            alarmService.createAlarm(comment.getMember(), member, comment.getPost().getIdx(), -1, AlarmType.COMMENTHEART, "");

        } else {
            // 좋아요 취소
            comment.getLikeUsers().remove(existingLike.get());
            comment.decreaseLike();
        }

        return commentRepository.save(comment);
    }



}
