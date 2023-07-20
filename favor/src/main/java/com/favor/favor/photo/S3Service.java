package com.favor.favor.photo;

import com.amazonaws.services.s3.AmazonS3;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class S3Service {
    @Value("${cloud.aws.s3.bucket")
    String bucketName;

    private final AmazonS3 amazonS3;

    public String uploadPhoto()
}
