package com.hibit2.hibit2.file.controller;

import com.hibit2.hibit2.file.service.FileUploadService;
import com.hibit2.hibit2.post.domain.Post;

import com.hibit2.hibit2.post.repository.PostRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
//게시글 사진 업로드 누른 경우 -> post가 먼저 작성되는 경우 사용
    /*
    @PostMapping(value = "/{post_idx}/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "이미지 저장", description = "이미지를 저장하는 메소드입니다.")
    public ResponseEntity<List<String>> uploadFiles(@PathVariable int post_idx, @RequestParam("file") List<MultipartFile> files,  @RequestParam int mainimgIdx) {
        List<String> fileUrls = new ArrayList<>();
        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
            String fileUrl = fileUploadService.uploadFile(file, post_idx);
            fileUrls.add(fileUrl);
        }
        Optional<Post> postOptional = postRepository.findById(post_idx);

        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            post.makeMainimg(fileUrls.get(mainimgIdx));
            fileUrls.remove(mainimgIdx);
            post.makeSubimg(fileUrls);
            postRepository.save(post);
        } else {
            throw new RuntimeException("게시글을 찾을 수 없습니다.");
        }

        return ResponseEntity.ok(fileUrls);
    }
*/
    //게시글 사진 업로드 누른 경우 -> 이미지 업로드 이후, dto에서 이미지가 전달받는 경우
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "이미지 저장", description = "이미지를 저장하는 메소드입니다.")
    public ResponseEntity<List<Object>> uploadFiles(@RequestParam("file") List<MultipartFile> files,  @RequestParam int mainimgIdx) {
        List<String> fileUrls = new ArrayList<>();
        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
            String fileUrl = fileUploadService.uploadFile(file);
            fileUrls.add(fileUrl);
        }
        String mainImg = fileUrls.remove(mainimgIdx);

        List<Object> response = new ArrayList<>();
        response.add(mainImg);
        response.add(fileUrls);
        return ResponseEntity.ok(response);
    }




}
