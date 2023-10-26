package com.hibit2.hibit2.post.service;


import com.hibit2.hibit2.matching.service.MatchingService;
import com.hibit2.hibit2.member.domain.Member;
import com.hibit2.hibit2.member.repository.MemberRepository;
import com.hibit2.hibit2.post.domain.Post;
import com.hibit2.hibit2.post.dto.PostListDto;
import com.hibit2.hibit2.post.dto.PostResponseDto;
import com.hibit2.hibit2.post.dto.PostSaveDto;
import com.hibit2.hibit2.post.dto.PostUpdateDto;
import com.hibit2.hibit2.post.exception.NotFoundPostException;
import com.hibit2.hibit2.post.repository.PostRepository;
import com.hibit2.hibit2.postHistory.domain.postHistory;
import com.hibit2.hibit2.postHistory.repository.postHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final postHistoryRepository postHistoryRepository;
    private final MatchingService matchingService;
    private final MemberRepository memberRepository;


    @Transactional
    public Post save(PostSaveDto postSaveDto, Long idx){

        Member member= memberRepository.getById(idx);


        postSaveDto.setMember(member);

        Post post = postSaveDto.toEntity();
        postRepository.save(post);

        postHistory postHistory = new postHistory();
        postHistory.setPost(post);
        postHistory.setOkUsers(new ArrayList<>());
        postHistory.setRealUsers(new ArrayList<>());
        postHistoryRepository.save(postHistory);

        return post;
    }

    @Transactional
    public List<PostListDto> findByPost_status(char flag){
        Sort sort = Sort.by(Sort.Direction.DESC,"createdDate");
        List<Post> list = postRepository.findByStatusNot(flag, sort);
        return list.stream().map(PostListDto::new).collect(Collectors.toList());
    }
    @Transactional
    public Page<PostListDto> findPostsByPost_status(char flag, Pageable pageable) {
        Page<Post> postPage = postRepository.findByStatusNot(flag, pageable);
        return postPage.map(PostListDto::new);
    }
    @Transactional
    public Page<PostListDto> findPostsByDateTimeRange(char flag, String startDate, String endDate, Pageable pageable) {
        LocalDate startLocalDate = LocalDate.parse(startDate);
        LocalDate endLocalDate = LocalDate.parse(endDate);
        endLocalDate = endLocalDate.plusDays(1);
        Page<Post> postPage = postRepository.findByDateTimeRange(flag, startLocalDate, endLocalDate, pageable);


        Set<Integer> uniquePostIds = new HashSet<>();
        List<PostListDto> uniquePostList = new ArrayList<>();

        for (Post post : postPage.getContent()) {
            if (uniquePostIds.add(post.getIdx())) {
                uniquePostList.add(new PostListDto(post));
            }
        }

        return new PageImpl<>(uniquePostList, pageable, uniquePostList.size());

    }

    //검색
    @Transactional
    public Page<PostListDto> findByTitleOrExhibition(char flag, String keyword, Pageable pageable) {
        Page<Post> postPage = postRepository.findByStatusNotAndTitleContainingOrExhibitionContaining(flag, keyword, keyword, pageable);
        return postPage.map(PostListDto::new);
    }

    @Transactional
    public PostResponseDto findById(int idx){
        Post entity= postRepository.findById(idx).orElseThrow(()-> new NotFoundPostException("해당 게시글이 없습니다. id="+idx));
        entity.increaseView();
        System.out.print(entity.getSubimg());
        return new PostResponseDto(entity);
    }

    @Transactional
    public Post update(int idx, PostUpdateDto requestDto){
        Post post = postRepository.findById(idx).orElseThrow(()-> new NotFoundPostException("해당 게시글이 없습니다. id="+idx));
        post.update(requestDto.getTitle(),requestDto.getContent(),  requestDto.getExhibiton(),requestDto.getNumber(), requestDto.getOpenchat(), requestDto.getWhat_do(),requestDto.getDateTimeSlots(),requestDto.getMainimg(), requestDto.getSubimg());
        return post;
    }

    @Transactional
    public void delete(int idx){
        Post entity = postRepository.findById(idx).orElseThrow(()-> new NotFoundPostException("해당 게시글이 없습니다. id="+idx));
        entity.delete();
    }
    @Transactional
    public Post likePost(int post_idx, Long member_idx){
        Post post = postRepository.findById(post_idx)
                .orElseThrow(() -> new NotFoundPostException());
        Member member= memberRepository.getById(member_idx);
        String userId = member.getNickname();

        if (post.getMember().getId().equals(member.getId())) {
            return post;
        }

        Optional<Member> existingLike = post.getLikeUsers().stream()
                .filter(likeUser -> likeUser.getNickname().equals(userId))
                .findFirst();

        if (!existingLike.isPresent()) {
            post.getLikeUsers().add(member);
            post.increaseLike();
        } else {
            post.getLikeUsers().remove(existingLike.get());
            post.decreaseLike();
        }
        return postRepository.save(post);
    }

    @Transactional
    public void completePost(int post_idx, Long member_idx) {
        Post post = postRepository.findById(post_idx)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));

        Member member= memberRepository.getById(member_idx);
        String userId = member.getNickname();

        if (post.getMember().getId().equals(member.getId())) {
            postHistory postHistory = postHistoryRepository.findByPostIdx(post_idx);

            postHistory.calculatePercent(postHistory.getOkNum(), postHistory.getNoNum());
            postHistory.complete();
            postHistory.setFinishTimeCurrent();

            List<Member> matchedUsers = matchingService.getMatchUserByPost(post_idx);
            postHistory.setOkUsers(matchedUsers);

            post.complete();
            postRepository.save(post);
            postHistoryRepository.save(postHistory);
        }
        else{
            new NotFoundPostException("게시글 작성자가 아닙니다.");
        }
    }

    @Transactional
    public void canclePost(int post_idx, Long member_idx) {
        Post post = postRepository.findById(post_idx)
                .orElseThrow(() -> new NotFoundPostException());

        Member member= memberRepository.getById(member_idx);
        String userId = member.getNickname();

        if (post.getMember().getId().equals(member.getId())) {

            postHistory postHistory = postHistoryRepository.findByPostIdx(post_idx);
            postHistory.cancle();
            postHistory.setFinishTimeCurrent();
            post.cancle();
            postRepository.save(post);
            postHistoryRepository.save(postHistory);
        }
        else{
            new NotFoundPostException("게시글 작성자가 아닙니다.");
        }
    }

}
