package com.favor.favor.photo;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.favor.favor.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

import static com.favor.favor.exception.ExceptionCode.SERVER_ERROR;

@Service
@RequiredArgsConstructor
public class PhotoService {
    private  String brandPhotoBucketName = "favor-app-bucket";
    private final AmazonS3Client amazonS3Client;

    /**
     * MultipartFile을 File로 전환 |
     * MultipartFile을 받아서 File의 형태로 전환하여 반환한다.
     */
    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convertingFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fileOutputStream = new FileOutputStream(convertingFile);
        fileOutputStream.write(file.getBytes());
        fileOutputStream.close();

        return convertingFile;
    }

    /**
     * S3에 파일 저장 |
     * 파일을 전환하고 특정 파일 관련된 폴더에 파일을 저장하고 URL을 반환한다. |
     * 500(SERVER_ERROR)
     */
    public String insertFileToS3(String bucketName, String fileName, MultipartFile file) {

        try {
            File convertedFile = convertMultiPartToFile(file);
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

    /**
     * 브랜드 사진 저장 |
     * 500(SERVER_ERROR)
     */
    @Transactional
    public Photo savePhoto(MultipartFile file) {

        String filename = file.getOriginalFilename();
        Photo photo = new Photo(filename);
        String originalFileName = photo.getFileName(filename);

        String brandPhotoUrl = insertFileToS3(brandPhotoBucketName, originalFileName, file);

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
