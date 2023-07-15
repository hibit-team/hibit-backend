package com.hibit2.hibit2.controller;

import com.hibit2.hibit2.domain.Post;
import com.hibit2.hibit2.repository.PostRepository;
import com.hibit2.hibit2.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController

public class FileUploadController {
    @Autowired
    private FileUploadService fileUploadService;
    @Autowired
    private PostRepository postRepository;
//게시글 사진 업로드 누른 경우
    @PostMapping("/{post_idx}/upload") //대표이미지 url 스트링값으로 받아오기
    public ResponseEntity<List<String>> uploadFiles(@PathVariable int post_idx, @RequestParam("file") List<MultipartFile> files, @RequestParam String mainimg) {
        List<String> fileUrls = new ArrayList<>();
        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
            String fileUrl = fileUploadService.uploadFile(file, post_idx);
            fileUrls.add(fileUrl);
        }
        Optional<Post> postOptional = postRepository.findById(post_idx);
        if (postOptional.isPresent()) {
            // 게시글이 존재하는 경우
            Post post = postOptional.get();
            post.makeMainimg(mainimg);
            postRepository.save(post); // 변경된 mainimg 값을 저장
        } else {
            // 게시글이 존재하지 않는 경우
            throw new RuntimeException("게시글을 찾을 수 없습니다.");
        }
        return ResponseEntity.ok(fileUrls);
    }

}
