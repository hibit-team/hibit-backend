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
import java.util.List;
import java.util.Optional;

@NoArgsConstructor
@Service
public class FileUploadService {

    @Value("${app.aws.s3.bucket}")
    private String bucketName;

    @Autowired
    @Qualifier("s3client")
    private AmazonS3 s3Client;

    //이미지 업로드 후 게시글 업로드
    public String uploadFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            // s3에 업로드
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());
            s3Client.putObject(new PutObjectRequest(bucketName, fileName, file.getInputStream(), metadata));
            URL fileUrl = s3Client.getUrl(bucketName, fileName);
            String imageUrl = fileUrl.toString();
            return imageUrl;
        } catch (IOException e) {
            throw new IllegalStateException("Failed to upload file", e);
        }

    }
}
