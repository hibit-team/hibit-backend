package com.hibit2.hibit2.file.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import com.hibit2.hibit2.file.domain.PostImage;
import com.hibit2.hibit2.file.repository.PostImageRepository;
import com.hibit2.hibit2.post.domain.Post;
import com.hibit2.hibit2.post.repository.PostRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

@NoArgsConstructor
@Service
public class FileUploadService {

    @Value("${app.aws.s3.bucket}")
    private String bucketName;

    @Autowired
    @Qualifier("s3client")

    private AmazonS3 s3Client;
    @Autowired

    private PostImageRepository postImageRepository;
    @Autowired

    private PostRepository postRepository;

    public String uploadFile(MultipartFile file, int post_idx) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            // s3에 업로드
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());
            s3Client.putObject(new PutObjectRequest(bucketName, fileName, file.getInputStream(), metadata));

            // 이미지 url 테이블 저장
            URL fileUrl = s3Client.getUrl(bucketName, fileName);
            String imageUrl = fileUrl.toString();
            PostImage postImage = new PostImage();
            Optional<Post> postOptional = postRepository.findById(post_idx);
            if (postOptional.isPresent()) {
                // 게시글이 존재하는 경우
                Post post = postOptional.get();
                postImage.setPost(post);
                postImage.setImageUrl(imageUrl);
                postImageRepository.save(postImage);
            } else {
                // 게시글이 존재하지 않는 경우
                throw new RuntimeException("게시글을 찾을 수 없습니다.");
            }

            return imageUrl;

        } catch (IOException e) {
            throw new IllegalStateException("Failed to upload file", e);
        }
    }
}
