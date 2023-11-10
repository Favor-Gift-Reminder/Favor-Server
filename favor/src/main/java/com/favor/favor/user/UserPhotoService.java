package com.favor.favor.user;

import com.favor.favor.exception.CustomException;
import com.favor.favor.photo.UserPhoto;
import com.favor.favor.photo.PhotoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.util.Optional;
import java.util.UUID;

import static com.favor.favor.exception.ExceptionCode.SERVER_ERROR;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserPhotoService {
    private final UserRepository userRepository;
    private final UserService userService;
    private final PhotoService photoService;

    //유저 프로필사진 등록/수정
    @Transactional
    public User updateUserProfilePhoto(Long userNo, MultipartFile file) {
        User user = userService.findUserByUserNo(userNo);
        if(!(user.getUserProfilePhoto() == null)){
            deleteUserProfilePhoto(userNo);
        }

        String fileName = file.getOriginalFilename();
        UserPhoto userPhoto = null;
        String storedFileName = getUserProfileFileName(fileName);

        String profilePhotoUrl = photoService.uploadFileToS3(storedFileName, file);
        try {
            userPhoto = UserPhoto.builder()
                    .photoUrl(profilePhotoUrl)
                    .build();
        } catch (RuntimeException e) {
            throw new CustomException(e, SERVER_ERROR);
        }

        user.setUserProfilePhoto(userPhoto);
        return userRepository.save(user);
    }

    //유저 프로필사진 조회
    public UserPhoto getUserProfilePhoto(Long userNo) {
        return userService.findUserByUserNo(userNo).getUserProfilePhoto();
    }

    //유저 프로필사진 삭제
    @Transactional
    public void deleteUserProfilePhoto(Long userNo) {

        User user = userService.findUserByUserNo(userNo);
        String fileName = extractProfilePhotoFileName(user);
        user.setUserProfilePhoto(null);
        photoService.deleteFileFromS3(fileName);
    }


    //유저 배경사진 등록/수정
    @Transactional
    public User updateUserBackgroundPhoto(Long userNo, MultipartFile file) {
        User user = userService.findUserByUserNo(userNo);
        //유저의 배경 사진이 이미 있으면 삭제
        if(!(user.getUserBackgroundPhoto() == null)){
            deleteUserBackgroundPhoto(userNo);
        }
        String filename = file.getOriginalFilename();
        UserPhoto userPhoto = null;
        String storedFileName = getUserBackgroundFileName(filename);

        String backgroundPhotoUrl = photoService.uploadFileToS3(storedFileName, file);
        try {
            userPhoto = UserPhoto.builder()
                    .photoUrl(backgroundPhotoUrl)
                    .build();
        } catch (RuntimeException e) {
            throw new CustomException(e, SERVER_ERROR);
        }

        user.setUserBackgroundPhoto(userPhoto);
        return userRepository.save(user);
    }

    //유저 배경사진 조회
    public UserPhoto getUserBackgroundPhoto(Long userNo) {
        return userService.findUserByUserNo(userNo).getUserBackgroundPhoto();
    }

    //유저 배경사진 삭제
    @Transactional
    public void deleteUserBackgroundPhoto(Long userNo) {

        User user = userService.findUserByUserNo(userNo);
        String fileName = extractBackgroundPhotoFileName(user);
        user.setUserBackgroundPhoto(null);
        photoService.deleteFileFromS3(fileName);
    }




    //유저 프로필 사진 이름 생성
    public String getUserProfileFileName(String fileName){
        return  "user_profile/"  + UUID.randomUUID()+ fileName.substring(fileName.lastIndexOf('.'));
    }
    //유저 배경 사진 이름 생성
    public String getUserBackgroundFileName(String fileName){
        return  "user_background/"  + UUID.randomUUID()+ fileName.substring(fileName.lastIndexOf('.'));
    }


    //유저의 프로필 사진 이름 추출
    public static String extractProfilePhotoFileName(User user) {
        String path;
        try {
            if (user.getUserProfilePhoto() == null) return "";
            path = new URL(user.getUserProfilePhoto().getPhotoUrl()).getPath();

            if (path.startsWith("/")) {
                path = path.substring(1);
            }
        } catch (Exception e) {
            throw new CustomException(e, SERVER_ERROR);
        }

        return path;
    }
    //유저의 배경 사진 이름 추출
    public static String extractBackgroundPhotoFileName(User user) {
        String path;

        try {
            if (user.getUserBackgroundPhoto() == null) return "";
            path = new URL(user.getUserBackgroundPhoto().getPhotoUrl()).getPath();

            if (path.startsWith("/")) {
                path = path.substring(1);
            }
        } catch (Exception e) {
            throw new CustomException(e, SERVER_ERROR);
        }

        return path;
    }
}
