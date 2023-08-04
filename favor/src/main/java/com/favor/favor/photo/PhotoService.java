package com.favor.favor.photo;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.favor.favor.exception.CustomException;
import com.favor.favor.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.UUID;

import static com.favor.favor.exception.ExceptionCode.FILE_NOT_FOUND;
import static com.favor.favor.exception.ExceptionCode.SERVER_ERROR;

@Service
@RequiredArgsConstructor
public class PhotoService {
    private  String bucketName = "favor-app-bucket";
    private final AmazonS3Client amazonS3Client;

    //S3에 사진 업로드
    public String uploadFileToS3(String fileName, MultipartFile file) {

        try {
            File convertedFile = convertFile(file);
            String uploadingFileName = fileName;

            amazonS3Client.putObject(new PutObjectRequest(bucketName, uploadingFileName, convertedFile)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            convertedFile.delete();
            String url = amazonS3Client.getUrl(bucketName, uploadingFileName).toString();
            return url;
        } catch (IOException e) {
            throw new CustomException(e, SERVER_ERROR);
        }
    }

    //S3 에서 사진 삭제
    public void deleteFileFromS3(String fileName) {
        try {
            amazonS3Client.deleteObject(bucketName, fileName);
        } catch (SdkClientException e) {
            throw new CustomException(e, FILE_NOT_FOUND);
        }
    }

    //fileName -> fileUrl
    public String createFileUrl(String fileName){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("https://favor-app-bucket.s3.ap-northeast-2.amazonaws.com/")
                .append(fileName);
        return stringBuilder.toString();
    }



    //MultipartFile을 File로 전환
    private File convertFile(MultipartFile file) throws IOException {
        File convertingFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fileOutputStream = new FileOutputStream(convertingFile);
        fileOutputStream.write(file.getBytes());
        fileOutputStream.close();

        return convertingFile;
    }







    //사진 저장 이름 생성
    public String getStoredFileName(String fileName){
        return UUID.randomUUID() + fileName.substring(fileName.lastIndexOf('.'));
    }


    //사진 저장
    @Transactional
    public Photo savePhoto(MultipartFile file) {

        String filename = file.getOriginalFilename();
        Photo photo = new Photo(filename);
        String storedFileName = getStoredFileName(filename);

        String brandPhotoUrl = uploadFileToS3(storedFileName, file);

        try {
            photo = Photo.builder()
                    .photoUrl(brandPhotoUrl)
                    .build();
        } catch (RuntimeException e) {
            throw new CustomException(e, SERVER_ERROR);
        }

        return photo;
    }
}
