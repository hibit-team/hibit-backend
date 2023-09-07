package com.hibit2.hibit2.file.controller;

import com.hibit2.hibit2.file.service.FileUploadService;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController

public class FileUploadController {
    @Autowired
    private FileUploadService fileUploadService;


    //게시글 사진 업로드 누른 경우 -> 이미지 업로드 이후, dto에서 이미지가 전달받는 경우
    @PostMapping(value = "/upload/{mainimgIdx}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "이미지 저장", description = "이미지를 저장하는 메소드입니다.")
    public ResponseEntity<List<Object>> uploadFiles(@RequestParam(value ="file", required=false) List<MultipartFile> files, @PathVariable int mainimgIdx) {
        if (files == null) {
            System.out.println("files is null");
        } else if (files.isEmpty()) {
            System.out.println("files is empty");
        }
        System.out.println("mainimgIdx: " + mainimgIdx);
        System.out.println("Number of files: " + files.size());

        List<String> fileUrls = new ArrayList<>();
        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
            String fileUrl = fileUploadService.uploadFile(file);
            System.out.println("File URL: " + fileUrl);
            fileUrls.add(fileUrl);
        }
        String mainImg = fileUrls.get(mainimgIdx);

        List<Object> response = new ArrayList<>();
        response.add(mainImg);
        response.add(fileUrls);
        return ResponseEntity.ok(response);
    }
    //게시글 수정 이미지 url
    @PostMapping(value = "/edit/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "이미지 저장(수정API)", description = "수정 API에서 이미지를 저장하는 메소드입니다.")
    public ResponseEntity<List<String>> edituploadFiles(@RequestParam(value ="file", required=false) List<MultipartFile> files) {
        if (files == null) {
            System.out.println("files is null");
        } else if (files.isEmpty()) {
            System.out.println("files is empty");
        }

        List<String> fileUrls = new ArrayList<>();
        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
            String fileUrl = fileUploadService.uploadFile(file);
            System.out.println("File URL: " + fileUrl);
            fileUrls.add(fileUrl);
        }
        return ResponseEntity.ok(fileUrls);
    }


    @PostMapping(value = "/uploadFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> uploadFile(@RequestPart(value = "file") MultipartFile multipartFile) {
        try {
            String imageUrl = fileUploadService.uploadFile(multipartFile);
            String jsonResponse = "{\"message\":\"Image uploaded successfully.\",\"imageUrl\":\"" + imageUrl + "\"}";

            // 업로드 성공 로그
            System.out.println("Image uploaded successfully. URL: " + imageUrl);

            return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
        } catch (Exception e) {
            String errorResponse = "{\"error\":\"UploadController().uploadFile().Exception : " + e.getMessage() + "\"}";

            // 업로드 실패 로그
            System.out.println("Image upload failed. Exception: " + e.getMessage());

            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
