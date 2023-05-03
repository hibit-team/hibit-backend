package com.hibit2.hibit2.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;

@Service
public class FileUploadService {

    @Value("${app.aws.s3.bucket}")
    private String bucketName;

    @Autowired
    @Qualifier("s3client")

    private AmazonS3 s3Client;

    public String uploadFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            // Upload the file to S3
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());
            s3Client.putObject(new PutObjectRequest(bucketName, fileName, file.getInputStream(), metadata));

            // Get the URL of the uploaded file
            URL fileUrl = s3Client.getUrl(bucketName, fileName);
            return fileUrl.toString();
        } catch (IOException e) {
            throw new IllegalStateException("Failed to upload file", e);
        }
    }
}
