package com.favor.favor.user;

import com.favor.favor.exception.CustomException;
import com.favor.favor.exception.ExceptionCode;
import com.favor.favor.photo.Photo;
import com.favor.favor.photo.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserPhotoService {

    private final UserRepository userRepository;
    private final PhotoService photoService;
    private final UserService userService;

    @Transactional
    public User updateUserPhoto(Long userNo, MultipartFile file) {
        User user = userService.findUserByUserNo(userNo);
        if(!(user.getUserPhoto() == null)){
            deleteUserPhoto(userNo);
        }
        Photo photo = photoService.saveUserProfilePhoto(file);
        user.setUserPhoto(photo);
        return userRepository.save(user);
    }

    public Photo getUserPhoto(Long userNo) {
        return userService.findUserByUserNo(userNo).getUserPhoto();
    }

    @Transactional
    public User deleteUserPhoto(Long userNo) {

        User user = userService.findUserByUserNo(userNo);
        String fileName = photoService.extractFileName(user.getUserPhoto().getPhotoUrl());

        user.setUserPhoto(null);
        photoService.deleteFileFromS3(fileName);

        return userRepository.save(user);
    }
}
