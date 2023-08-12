package com.hibit2.hibit2.post.service;


import com.hibit2.hibit2.global.repository.MatchingRepository;
import com.hibit2.hibit2.matching.domain.MatchStatus;
import com.hibit2.hibit2.matching.domain.Matching;
import com.hibit2.hibit2.matching.service.MatchingService;
import com.hibit2.hibit2.post.domain.Post;
import com.hibit2.hibit2.post.dto.PostListDto;
import com.hibit2.hibit2.post.dto.PostResponseDto;
import com.hibit2.hibit2.post.dto.PostSaveDto;
import com.hibit2.hibit2.post.dto.PostUpdateDto;
import com.hibit2.hibit2.post.repository.PostRepository;
import com.hibit2.hibit2.postHistory.domain.postHistory;
import com.hibit2.hibit2.postHistory.repository.postHistoryRepository;
import com.hibit2.hibit2.user.domain.Users;
import com.hibit2.hibit2.user.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final UsersRepository usersRepository;
    private final MatchingRepository matchingRepository;
    private final postHistoryRepository postHistoryRepository;
    private final MatchingService matchingService;

    @Transactional
    public Post save(PostSaveDto postSaveDto){

        Users user = usersRepository.findById("a")
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        usersRepository.save(user);
        postSaveDto.setUser(user);

        Post post = postSaveDto.toEntity();
        postRepository.save(post);

        postHistory postHistory = new postHistory();
        postHistory.setPost(post);
        postHistory.setOkUsers(new ArrayList<>());
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
        return postPage.map(PostListDto::new);
    }

    //검색
    @Transactional
    public Page<PostListDto> findByTitleOrExhibition(char flag, String keyword, Pageable pageable) {
        Page<Post> postPage = postRepository.findByStatusNotAndTitleContainingOrExhibitionContaining(flag, keyword, keyword, pageable);
        return postPage.map(PostListDto::new);
    }

    @Transactional
    public PostResponseDto findById(int idx){
        Post entity= postRepository.findById(idx).orElseThrow(()-> new IllegalArgumentException("해당 게시글이 없습니다. id="+idx));
        entity.increaseView();
        System.out.print(entity.getSubimg());
        return new PostResponseDto(entity);
    }

    @Transactional
    public Post update(int idx, PostUpdateDto requestDto){
        Post post = postRepository.findById(idx).orElseThrow(()-> new IllegalArgumentException("해당 게시글이 없습니다. id="+idx));
        post.update(requestDto.getTitle(),requestDto.getContent(),  requestDto.getExhibiton(),requestDto.getNumber(), requestDto.getOpenchat(), requestDto.getWhat_do(),requestDto.getDateTimeSlots(),requestDto.getMainimg(), requestDto.getSubimg());
        return post;
    }

    @Transactional
    public void delete(int idx){
        Post entity = postRepository.findById(idx).orElseThrow(()-> new IllegalArgumentException("해당 게시글이 없습니다. id="+idx));
        entity.delete();
    }
    @Transactional
    public Post likePost(int post_idx, int user_idx){
        Post post = postRepository.findById(post_idx)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        Users user = usersRepository.findById(user_idx)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        String userId = user.getId();

        Optional<Users> existingLike = post.getLikeUsers().stream()
                .filter(likeUser -> likeUser.getId().equals(userId))
                .findFirst();

        if (!existingLike.isPresent()) {
            post.getLikeUsers().add(user);
            post.increaseLike();
        } else {
            post.getLikeUsers().remove(existingLike.get());
            post.decreaseLike();
        }
        return postRepository.save(post);
    }

    @Transactional
    public void completePost(int post_idx) {
        Post post = postRepository.findById(post_idx)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));

        postHistory postHistory = postHistoryRepository.findByPostIdx(post_idx);
        if (postHistory.getOkNum() == 0) {
            throw new RuntimeException("매칭이 진행되지 않았습니다. 매칭 진행 이후 모집 완료를 눌러주세요.");
        }

        postHistory.calculatePercent(postHistory.getOkNum(), postHistory.getNoNum());
        postHistory.complete();
        List<Matching> matchingList = matchingRepository.findByPostIdxAndStatus(post_idx, MatchStatus.OK);
        List<String> matchedUsers = matchingService.getMatchUserByPost(post_idx);


        postHistory.setOkUsers(matchedUsers);
        LocalDateTime currentTime = LocalDateTime.now();
        postHistory.setFinishTime(currentTime);

        post.complete();
        postRepository.save(post);
        postHistoryRepository.save(postHistory);

    }

    @Transactional
    public void canclePost(int post_idx) {
        Post post = postRepository.findById(post_idx)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        postHistory postHistory = postHistoryRepository.findByPostIdx(post_idx);
        postHistory.cancle();
        LocalDateTime currentTime = LocalDateTime.now();
        postHistory.setFinishTime(currentTime);
        post.cancle();
        postRepository.save(post);
        postHistoryRepository.save(postHistory);

    }

}
