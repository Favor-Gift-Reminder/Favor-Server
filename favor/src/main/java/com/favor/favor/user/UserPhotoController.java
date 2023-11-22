package com.favor.favor.user;

import com.favor.favor.common.DefaultResponseDto;
import com.favor.favor.photo.UserPhoto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.favor.favor.common.DefaultResponseDto.*;

@Api(tags = "User-Photo")
@RestController
@RequestMapping("/user-photos")
@RequiredArgsConstructor
public class UserPhotoController {
    private final UserPhotoService userPhotoService;
    private final UserService userService;

    @ApiOperation(value = "회원 프로필 사진 수정")
    @ApiResponses(value={
            @ApiResponse(code = 201,
                    message = "USER_PROFILE_PHOTO_UPDATED",
                    response = UserResponseDto.class),
            @ApiResponse(code = 400,
                    message = "FIELD_REQUIRED / *_CHARACTER_INVALID / *_LENGTH_INVALID"),
            @ApiResponse(code = 401,
                    message = "UNAUTHORIZED_USER"),
            @ApiResponse(code = 404,
                    message = "USER_NOT_FOUND"),
            @ApiResponse(code = 500,
                    message = "SERVER_ERROR")
    })
    @PostMapping("/profile")
    public ResponseEntity<DefaultResponseDto<Object>> updateUserProfilePhoto(
            @ModelAttribute MultipartFile file,
            @AuthenticationPrincipal User loginUser
    ) {
        Long userNo = loginUser.getUserNo();
        userService.isExistingUserNo(userNo);

        User user = userPhotoService.updateUserProfilePhoto(userNo, file);
        UserPhoto dto = user.getUserProfilePhoto();

        return ResponseEntity.status(201)
                .body(resWithData("USER_PROFILE_PHOTO_UPDATED", "회원 사진 수정 완료", dto));
    }

    @ApiOperation("회원 프로필 사진 조회")
    @ApiResponses(value={
            @ApiResponse(code = 200,
                    message = "USER_PROFILE_PHOTO_FOUND",
                    response = UserResponseDto.class),
            @ApiResponse(code = 401,
                    message = "UNAUTHORIZED_USER"),
            @ApiResponse(code = 404,
                    message = "USER_NOT_FOUND"),
            @ApiResponse(code = 500,
                    message = "SERVER_ERROR")
    })
    @GetMapping("/profile")
    public ResponseEntity<DefaultResponseDto<Object>> getUserProfilePhoto(
            @AuthenticationPrincipal User loginUser
    ) {
        UserPhoto dto = userPhotoService.getUserProfilePhoto(loginUser.getUserNo());

        return ResponseEntity.status(200)
                .body(resWithData("USER_PROFILE_PHOTO_FOUND", "회원 사진 조회 완료", dto));
    }

    @ApiOperation("회원 프로필 사진 삭제")
    @ApiResponses(value={
            @ApiResponse(code = 200,
                    message = "USER_PROFILE_PHOTO_DELETED",
                    response = UserResponseDto.class),
            @ApiResponse(code = 401,
                    message = "UNAUTHORIZED_USER"),
            @ApiResponse(code = 404,
                    message = "USER_NOT_FOUND"),
            @ApiResponse(code = 500,
                    message = "SERVER_ERROR")
    })
    @DeleteMapping("/profile")
    public ResponseEntity<DefaultResponseDto<Object>> deleteUserProfilePhoto(@AuthenticationPrincipal User loginUser)
    {
        Long userNo = loginUser.getUserNo();

        userPhotoService.deleteUserProfilePhoto(userNo);

        return ResponseEntity.status(200)
                .body(resWithoutData("USER_PROFILE_PHOTO_DELETED", "회원 사진 삭제 완료"));

    }

    @ApiOperation(value = "회원 배경 사진 수정")
    @ApiResponses(value={
            @ApiResponse(code = 201,
                    message = "USER_BACKGROUND_PHOTO_UPDATED",
                    response = UserResponseDto.class),
            @ApiResponse(code = 400,
                    message = "FIELD_REQUIRED / *_CHARACTER_INVALID / *_LENGTH_INVALID"),
            @ApiResponse(code = 401,
                    message = "UNAUTHORIZED_USER"),
            @ApiResponse(code = 404,
                    message = "USER_NOT_FOUND"),
            @ApiResponse(code = 500,
                    message = "SERVER_ERROR")
    })
    @PostMapping("/background")
    public ResponseEntity<DefaultResponseDto<Object>> updateUserBackgroundPhoto(
            @ModelAttribute MultipartFile file,
            @AuthenticationPrincipal User loginUser
    ) {
        Long userNo = loginUser.getUserNo();
        userService.isExistingUserNo(userNo);

        User user = userPhotoService.updateUserBackgroundPhoto(userNo, file);
        UserPhoto dto = user.getUserBackgroundPhoto();

        return ResponseEntity.status(201)
                .body(resWithData("USER_BACKGROUND_PHOTO_UPDATED", "회원 배경 사진 수정 완료", dto));
    }

    @ApiOperation("회원 배경 사진 조회")
    @ApiResponses(value={
            @ApiResponse(code = 200,
                    message = "USER_BACKGROUND_PHOTO_FOUND",
                    response = UserResponseDto.class),
            @ApiResponse(code = 401,
                    message = "UNAUTHORIZED_USER"),
            @ApiResponse(code = 404,
                    message = "USER_NOT_FOUND"),
            @ApiResponse(code = 500,
                    message = "SERVER_ERROR")
    })
    @GetMapping("/background")
    public ResponseEntity<DefaultResponseDto<Object>> getUserBackgroundPhoto(
            @AuthenticationPrincipal User loginUser
    ) {
        UserPhoto dto = userPhotoService.getUserBackgroundPhoto(loginUser.getUserNo());

        return ResponseEntity.status(200)
                .body(resWithData("USER_BACKGROUND_PHOTO_FOUND", "회원 배경 사진 조회 완료", dto));
    }

    @ApiOperation("회원 배경 사진 삭제")
    @ApiResponses(value={
            @ApiResponse(code = 200,
                    message = "USER_BACKGROUND_PHOTO_DELETED",
                    response = UserResponseDto.class),
            @ApiResponse(code = 401,
                    message = "UNAUTHORIZED_USER"),
            @ApiResponse(code = 404,
                    message = "USER_NOT_FOUND"),
            @ApiResponse(code = 500,
                    message = "SERVER_ERROR")
    })
    @DeleteMapping("/background")
    public ResponseEntity<DefaultResponseDto<Object>> deleteUserBackgroundPhoto(
            @AuthenticationPrincipal User loginUser
    ) {
        Long userNo = loginUser.getUserNo();

        userPhotoService.deleteUserBackgroundPhoto(userNo);

        return ResponseEntity.status(200)
                .body(resWithoutData("USER_BACKGROUND_PHOTO_DELETED", "회원 배경 사진 수정 완료"));
    }
}