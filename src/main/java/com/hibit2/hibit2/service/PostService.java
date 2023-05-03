package com.hibit2.hibit2.service;

import com.hibit2.hibit2.domain.Post;
import com.hibit2.hibit2.dto.PostResponseDto;
import com.hibit2.hibit2.dto.PostSaveDto;
import com.hibit2.hibit2.dto.PostUpdateDto;
import com.hibit2.hibit2.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    //UserRepository userrepository

    @Transactional
    public int save(PostSaveDto postSaveDto){

        //postSaveDto.setUser(user) 위에 Users user추가
        Post post = postSaveDto.toEntity();
        postRepository.save(post);
        return post.getIdx();
    }

    @Transactional
    public List<PostResponseDto> findByDeleteYn(final char deleteYn){
        Sort sort = Sort.by(Sort.Direction.DESC,"createdDate");
        List<Post> list = postRepository.findALlByDeleteYn(deleteYn, sort);
        return list.stream().map(PostResponseDto::new).collect(Collectors.toList());
    }

    @Transactional
    public PostResponseDto findById(int idx){
        Post entity= postRepository.findById(idx).orElseThrow(()-> new IllegalArgumentException("해당 게시글이 없습니다. id="+idx));
        entity.increaseView();
        return new PostResponseDto(entity);
    }

    @Transactional
    public int update(int idx, PostUpdateDto requestDto){
        Post post = postRepository.findById(idx).orElseThrow(()-> new IllegalArgumentException("해당 게시글이 없습니다. id="+idx));
        post.update(requestDto.getTitle(),requestDto.getContent(), requestDto.getNumber(), requestDto.getOpenchat(), requestDto.getWhat_do());
        return idx;
    }

    @Transactional
    public int delete(int idx){
        Post entity = postRepository.findById(idx).orElseThrow(()-> new IllegalArgumentException("해당 게시글이 없습니다. id="+idx));
        entity.delete();
        return idx;
    }



}
