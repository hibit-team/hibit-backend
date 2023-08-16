package com.hibit2.hibit2.post.controller;



import com.hibit2.hibit2.member.domain.Member;
import com.hibit2.hibit2.member.repository.MemberRepository;
import com.hibit2.hibit2.post.domain.Post;
import com.hibit2.hibit2.post.dto.PostListDto;
import com.hibit2.hibit2.post.dto.PostResponseDto;
import com.hibit2.hibit2.post.dto.PostSaveDto;
import com.hibit2.hibit2.post.dto.PostUpdateDto;
import com.hibit2.hibit2.post.service.PostService;
import com.hibit2.hibit2.postHistory.domain.postHistory;
import com.hibit2.hibit2.postHistory.repository.postHistoryRepository;
import com.hibit2.hibit2.user.domain.Users;
import com.hibit2.hibit2.user.repository.UsersRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Tag(name = "post", description = "매칭 게시글 작성")
@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;
    private final postHistoryRepository postHistoryRepository;
    private final UsersRepository usersRepository;
    private final MemberRepository memberRepository;


    @PostMapping("/write/{user_idx}")
    @Operation(summary = "post/write/{user_idx}", description = "매칭 게시글 작성")
    @Parameters({@Parameter(name = "title", description = "제목", example = "디뮤지엄 전시 보러가요"),
                @Parameter(name = "content", description = "내용", example =  "본문내용"),
                @Parameter(name = "number", description = "관람 인원", example =  "3"),
                @Parameter(name = "openchat", description = "오픈채팅 url", example =  "http://kakao"),
                @Parameter(name = "what_do", description = "전시보고뭐할래", example =  "EAT"),
                @Parameter(name = "dateTimeSlots", description = "날짜", example =  "[\n" + "{\n" +
                        "    \"date\": \"2023-05-31\",\n" +
                        "    \"timeSlot\": \"AM\"\n" +
                        "  }" +"\n]"),
               @Parameter(name = "mainimg", description = "대표이미지url", example ="hibitbucket"),
               @Parameter(name = "subimg", description = "서브이미지URL 리스트", example ="hibitbucket")
    })
    public ResponseEntity<Post> save(@RequestBody PostSaveDto requestDto,@PathVariable Long user_idx){

        Post post = postService.save(requestDto, user_idx);
        return ResponseEntity.status(HttpStatus.CREATED).body(post);

    }
    //기본 게시글 리스트
    @GetMapping("/list/allposts/{pageParam}")
    @Operation(summary = "post/list/allposts/1", description = "매칭글 기본 리스트")
    public ResponseEntity<List<PostListDto>> findPostsByPage(@PathVariable int pageParam) {
        char flag = 'D';
        int page = pageParam -1;
        Pageable pageable = PageRequest.of(page, 6, Sort.by(Sort.Direction.DESC, "createdDate"));
        Page<PostListDto> postPage = postService.findPostsByPost_status(flag, pageable);
        return ResponseEntity.status(HttpStatus.CREATED).body(postPage.getContent());

    }

    //게시글 좋아요 순 리스트
    @GetMapping("/list/like/{pageParam}")
    @Operation(summary = "post/list/like/1", description = "매칭글 좋아요 순서 리스트")
    public ResponseEntity<List<PostListDto>> findPostsByPageLike(@PathVariable int pageParam) {
        char flag = 'D';
        int page = pageParam -1;
        Pageable pageable = PageRequest.of(page, 6, Sort.by(Sort.Direction.DESC, "liked").and(Sort.by(Sort.Direction.DESC, "createdDate")));
        Page<PostListDto> postPage = postService.findPostsByPost_status(flag, pageable);
        return ResponseEntity.status(HttpStatus.CREATED).body(postPage.getContent());

    }
    //이번주 출발 게시글 리스트
    @GetMapping("/list/thisweek/{pageParam}")
    @Operation(summary = "post/thisweek/1", description = "매칭글 이번주 출발 리스트")
    public ResponseEntity<List<PostListDto>> findPostsByDateTimeRange(@PathVariable int pageParam) {
        char flag = 'D';
        int page = pageParam -1;
        Pageable pageable = PageRequest.of(page, 6, Sort.by(Sort.Direction.DESC, "createdDate"));
        // 현재 날짜와 이번 주의 월요일을 가져옴
        LocalDate currentDate = LocalDate.now();
        LocalDate monday = currentDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        // 이번 주 월요일부터 일요일까지의 범위로 날짜를 필터링
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String mondayStr = monday.format(formatter);
        String sundayStr = monday.plusDays(6).format(formatter);

        Page<PostListDto> postPage = postService.findPostsByDateTimeRange(flag, mondayStr, sundayStr, pageable);

        return ResponseEntity.status(HttpStatus.CREATED).body(postPage.getContent());

    }

    //게시글 전체보기
    @GetMapping("/listall")
    @Operation(summary = "post/listall", description = "게시글 전체보기, 기본 최신순")
    public ResponseEntity<List<PostListDto>> findAllPosts(){
        char flag = 'D';
        List<PostListDto> list = postService.findByPost_status(flag);
        return ResponseEntity.status(HttpStatus.CREATED).body(list);
    }

    // 검색
    @GetMapping("/list/search/{pageParam}")
    @Operation(summary = "post/list/search/1", description = "검색 결과")
    public ResponseEntity<List<PostListDto>> findByTitleOrExhibition(@PathVariable int pageParam, @RequestParam String keyword) {
        char flag = 'D';
        int page = pageParam -1;
        Pageable pageable = PageRequest.of(page, 6, Sort.by(Sort.Direction.DESC, "createdDate"));
        Page<PostListDto> postPage = postService.findByTitleOrExhibition(flag,keyword,pageable);
        return ResponseEntity.status(HttpStatus.CREATED).body(postPage.getContent());

    }
    @GetMapping("/{post_idx}")
    @Operation(summary = "post/{idx}", description = "매칭 글 상세")
    public ResponseEntity<PostResponseDto> findById(@PathVariable int post_idx){
        PostResponseDto dto = postService.findById(post_idx);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);

    }

    @PutMapping("/{post_idx}")
    @Operation(summary = "post/{post_idx}",description = "매칭 글 수정")
    public ResponseEntity<Post> update(@PathVariable int post_idx, @RequestBody PostUpdateDto requestDto){
        Post post = postService.update(post_idx, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }

    @DeleteMapping("/{post_idx}")
    @Operation(summary = "post/{post_idx}", description = "매칭 글 삭제")
    public ResponseEntity<Void> delete(@PathVariable int post_idx){
        postService.delete(post_idx);
        return ResponseEntity.ok().build();

    }

    //게시글 좋아요
    @GetMapping("/{post_idx}/{member_idx}/like")
    @Operation(summary = "post/{post_idx}/{member_idx}/like", description = "좋아요 누르기, 테스트할려면 유저 signup에서 b로 회원가입하기")
    public ResponseEntity<PostResponseDto> likeComment(@PathVariable int post_idx, @PathVariable Long member_idx){
        Post post = postService.likePost(post_idx, member_idx);
        PostResponseDto postResponseDto = new PostResponseDto(post);
        return ResponseEntity.ok(postResponseDto);
    }

    //게시글 상태 변경
    @PutMapping("/{post_idx}/complete")
    @Operation(summary = "/post/1/complete", description = "게시글 모집 완료")
    public ResponseEntity<String> completePost(@PathVariable int post_idx) {
        postHistory postHistory = postHistoryRepository.findByPostIdx(post_idx);
        if (postHistory.getOkNum() == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("매칭이 진행되지 않았습니다. 매칭 진행 이후 모집 완료를 눌러주세요.");
        }

        postService.completePost(post_idx);

        return ResponseEntity.ok().build();
    }

    //게시글 취소 변경
    @PutMapping("/{post_idx}/cancle")
    @Operation(summary = "/post/1/cancle", description = "게시글 모집 완료")
    public ResponseEntity<String> canclePost(@PathVariable int post_idx) {
        postHistory postHistory = postHistoryRepository.findByPostIdx(post_idx);
        postService.canclePost(post_idx);
        return ResponseEntity.ok().build();
    }




}
