package com.favor.favor.photo;

import com.amazonaws.services.s3.AmazonS3;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {

    @Value("${cloud.aws.s3.bucket")
    private String bucketName;

    private final AmazonS3 s3;

    public String uploadPhoto(File file){

        String filename = createStoredFileName(file);
        String uploadedFileName = putS3(file, filename);

        return uploadedFileName + " 업로드 완료";
    }

    public String putS3(File file, String fileName){
        //multipartFile(X)
        s3.putObject(bucketName, fileName, file);
        return fileName;
    }

    //확장명 제거한 순수한 파일명 반환
    public String createStoredFileName(File file){

        String originalFileName = file.getName();

        int extensionIndex = originalFileName.lastIndexOf('.');
        String fileName = originalFileName.substring(extensionIndex);

        return fileName;
    }
}
