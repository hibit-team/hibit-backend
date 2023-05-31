package com.hibit2.hibit2.controller;


import com.hibit2.hibit2.domain.Post;
import com.hibit2.hibit2.dto.PostListDto;
import com.hibit2.hibit2.dto.PostResponseDto;
import com.hibit2.hibit2.dto.PostSaveDto;
import com.hibit2.hibit2.dto.PostUpdateDto;
import com.hibit2.hibit2.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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


    @PostMapping("/write")
    //@Secured({"ROLE_USER"})
    @Operation(summary = "post/write", description = "매칭 게시글 작성")
    @Parameters({@Parameter(name = "title", description = "제목", example = "디뮤지엄 전시 보러가요"),
                @Parameter(name = "content", description = "내용", example =  "본문내용"),
                @Parameter(name = "number", description = "관람 인원", example =  "3"),
                @Parameter(name = "openchat", description = "오픈채팅 url", example =  "http://kakao"),
                @Parameter(name = "what_do", description = "전시보고뭐할래", example =  "EAT")
    })
    public Post save(@RequestBody PostSaveDto requestDto){
        //Users user = (Users) authentication.getPrincipal();
        return postService.save(requestDto);
    }
    //기본 게시글 리스트
    @GetMapping("/list/{pageParam}")
    @Operation(summary = "post/list", description = "매칭글 기본 리스트")
    public List<PostListDto> findPostsByPage(@PathVariable int pageParam) {
        char flag = 'N';
        int page = pageParam -1;
        Pageable pageable = PageRequest.of(page, 6, Sort.by(Sort.Direction.DESC, "createdDate"));
        Page<PostListDto> postPage = postService.findPostsByDeleteYn(flag, pageable);
        return postPage.getContent();
    }

    //게시글 좋아요 순 리스트
    @GetMapping("/list/like/{pageParam}")
    @Operation(summary = "post/list/like/1", description = "매칭글 좋아요 순서 리스트")
    public List<PostListDto> findPostsByPageLike(@PathVariable int pageParam) {
        char flag = 'N';
        int page = pageParam -1;
        Pageable pageable = PageRequest.of(page, 6, Sort.by(Sort.Direction.DESC, "liked").and(Sort.by(Sort.Direction.DESC, "createdDate")));
        Page<PostListDto> postPage = postService.findPostsByDeleteYn(flag, pageable);
        return postPage.getContent();
    }
    //이번주 출발 게시글 리스트
    @GetMapping("/list/thisweek/{pageParam}")
    @Operation(summary = "post/thisweek/1", description = "매칭글 이번주 출발 리스트")
    public List<PostListDto> findPostsByDateTimeRange(@PathVariable int pageParam) {
        char flag = 'N';
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

        return postPage.getContent();
    }

    //게시글 전체보기
    @GetMapping("/listall")
    @Operation(summary = "post/listall", description = "게시글 전체보기, 기본 최신순")
    public List<PostListDto> findAllPosts(){
        char flag = 'N';
        return postService.findByDeleteYn(flag);
    }

    @GetMapping("/{idx}")
    @Operation(summary = "post/{idx}", description = "매칭 글 상세")
    public PostResponseDto findById(@PathVariable int idx){
        return postService.findById(idx);
    }

    @PutMapping("/{idx}")
    @Operation(summary = "post/{idx}",description = "매칭 글 수정")
    public int update(@PathVariable int idx, @RequestBody PostUpdateDto requestDto){
        return postService.update(idx, requestDto);
    }

    @DeleteMapping("/{idx}")
    @Operation(summary = "post/{idx}", description = "매칭 글 삭제")
    public int delete(@PathVariable int idx){
        return postService.delete(idx);
    }
    //게시글 좋아요
    @GetMapping("/{post_idx}/like")
    public ResponseEntity<Post> likeComment(@PathVariable int post_idx){
        String user_id = "b"; //나중에는 현재 로그인한 유저의 id 찾아오기
        Post post = postService.likePost(post_idx, user_id);
        return ResponseEntity.ok(post);
    }

}
