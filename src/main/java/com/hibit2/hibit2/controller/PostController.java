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
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
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
    @Operation(summary = "post/list", description = "매칭 글 리스트")
    public List<PostListDto> findPostsByPage(@PathVariable int pageParam) {
        char flag = 'N';
        int page = pageParam -1;
        Pageable pageable = PageRequest.of(page, 6, Sort.by(Sort.Direction.DESC, "createdDate"));
        Page<PostListDto> postPage = postService.findPostsByDeleteYn(flag, pageable);
        return postPage.getContent();
    }

    //게시글 전체보기
    @GetMapping("/listall")
    @Operation(summary = "post/list", description = "게시글 전체보기")
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
}
