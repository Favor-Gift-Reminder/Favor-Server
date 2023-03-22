package com.favor.favor.User;


import com.favor.favor.Common.DefaultResponseDto;
import com.favor.favor.Enum.Category;
import com.favor.favor.Enum.Emotion;
import com.favor.favor.Friend.FriendResponseDto;
import com.favor.favor.Gift.GiftDetailResponseDto;
import com.favor.favor.Gift.GiftResponseDto;
import com.favor.favor.Reminder.ReminderResponseDto;
import io.swagger.annotations.*;
import jdk.jfr.ContentType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@Api(tags = "User")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @ApiOperation(value = "회원가입")
    @ApiResponses(value={
            @ApiResponse(code = 201,
                    message = "USER_REGISTERED",
                    response = UserResponseDto.class),
            @ApiResponse(code = 400,
                    message = "FILED_REQUIRED / *_CHARACTER_INVALID / *_LENGTH_INVALID"),
            @ApiResponse(code = 401,
                    message = "UNAUTHORIZED_EMAIL"),
            @ApiResponse(code = 404,
                    message = "EMAIL_NOT_FOUND"),
            @ApiResponse(code = 409,
                    message = "DUPLICATE_EMAIL"),
            @ApiResponse(code = 500,
                    message = "SERVER_ERROR")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/sign-up")
    public ResponseEntity<DefaultResponseDto<Object>> signUp(
            @RequestBody @Valid SignUpDto signUpDto
    ) {

        userService.isExistingEmail(signUpDto.getEmail());

        User user = userService.signUp(signUpDto);
        UserDetailResponseDto dto = userService.returnUserDetailDto(user);

        return ResponseEntity.status(201)
                .body(DefaultResponseDto.builder()
                        .responseCode("USER_REGISTERED")
                        .responseMessage("회원가입 완료")
                        .data(dto)
                        .build());
    }

    @ApiOperation("프로필생성")
    @ApiResponses(value={
            @ApiResponse(code = 201,
                    message = "PROFILE_CREATED",
                    response = UserResponseDto.class),
            @ApiResponse(code = 400,
                    message = "FILED_REQUIRED / *_CHARACTER_INVALID / *_LENGTH_INVALID"),
            @ApiResponse(code = 401,
                    message = "UNAUTHORIZED_EMAIL"),
            @ApiResponse(code = 404,
                    message = "USER_NOT_FOUND"),
            @ApiResponse(code = 409,
                    message = "DUPLICATE_ID"),
            @ApiResponse(code = 500,
                    message = "SERVER_ERROR")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    @PatchMapping("/profile")
    public ResponseEntity<DefaultResponseDto<Object>> createProfile(
            @RequestBody @Valid ProfileDto profileDto, Long userNo) {

        userService.isExistingUserId(profileDto.getUserId());

        User user = userService.createProfile(profileDto, userNo);
        UserDetailResponseDto dto = userService.returnUserDetailDto(user);

        return ResponseEntity.status(201)
                .body(DefaultResponseDto.builder()
                        .responseCode("PROFILE_CREATED")
                        .responseMessage("프로필 생성")
                        .data(dto)
                        .build());
    }


    @ApiOperation("단일 회원 조회")
    @ApiResponses(value={
            @ApiResponse(code = 201,
                    message = "USER_FOUND",
                    response = UserResponseDto.class),
            @ApiResponse(code = 401,
                    message = "UNAUTHORIZED_USER"),
            @ApiResponse(code = 404,
                    message = "USER_NOT_FOUND"),
            @ApiResponse(code = 500,
                    message = "SERVER_ERROR")
    })
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    @GetMapping("/{userNo}")
    public ResponseEntity<DefaultResponseDto<Object>> readUser(
            @PathVariable Long userNo){

        userService.isExistingUserNo(userNo);

        User user = userService.readUser(userNo);
        UserDetailResponseDto dto = userService.returnUserDetailDto(user);

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("USER_FOUND")
                        .responseMessage("단일 회원 조회 완료")
                        .data(dto)
                        .build());
    }

    @ApiOperation("회원 수정")
    @ApiResponses(value={
            @ApiResponse(code = 201,
                    message = "USER_FOUND",
                    response = UserResponseDto.class),
            @ApiResponse(code = 401,
                    message = "UNAUTHORIZED_USER"),
            @ApiResponse(code = 404,
                    message = "USER_NOT_FOUND"),
            @ApiResponse(code = 500,
                    message = "SERVER_ERROR")
    })
    @Transactional
    @PatchMapping("/{userNo}")
    public ResponseEntity<DefaultResponseDto<Object>> updateUser(
            @PathVariable Long userNo, 
            @RequestBody UserUpdateRequestDto userUpdateRequestDto){
        
        userService.isExistingUserNo(userNo);

        User user = userService.readUser(userNo);
        userService.updateUser(user, userUpdateRequestDto);

        UserDetailResponseDto dto = userService.returnUserDetailDto(user);

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("USER_UPDATED")
                        .responseMessage("회원 수정 완료")
                        .data(dto)
                        .build());

    }

    @ApiOperation("회원 탈퇴")
    @ApiResponses(value={
            @ApiResponse(code = 200,
                    message = "USER_DELETED",
                    response = UserResponseDto.class),
            @ApiResponse(code = 401,
                    message = "UNAUTHORIZED_USER"),
            @ApiResponse(code = 404,
                    message = "USER_NOT_FOUND"),
            @ApiResponse(code = 500,
                    message = "SERVER_ERROR")
    })
    @Transactional
    @DeleteMapping("/{userNo}")
    public ResponseEntity<DefaultResponseDto<Object>> deleteUser(
            @PathVariable Long userNo){

        userService.isExistingUserNo(userNo);

        User user = userService.findUserByUserNo(userNo);
        UserDetailResponseDto dto = userService.returnUserDetailDto(user);

        userService.deleteUser(userNo);

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("USER_DELETED")
                        .responseMessage("회원 탈퇴 완료 (임시)")
                        .data(dto)
                        .build());
    }


    @ApiOperation("회원의 리마인더 전체 조회")
    @GetMapping("/reminder-list/{userNo}")
    public List<ReminderResponseDto> readReminderList(@PathVariable Long userNo){
        return userService.readReminderList(userNo);
    }

    @ApiOperation("회원의 선물 전체 조회")
    @GetMapping("/gift-list/{userNo}")
    public List<GiftResponseDto> readGiftList(@PathVariable Long userNo){
        return userService.readGiftList(userNo);
    }

    @ApiOperation("회원의 친구 전체 조회")
    @GetMapping("/friend-list/{userNo}")
    public List<FriendResponseDto> readFriendList(@PathVariable Long userNo){
        return userService.readFriendList(userNo);
    }


    @ApiOperation("전체 회원 조회")
    @GetMapping
    public List<UserResponseDto> readAll(){
        return userService.readAll();
    }


    @ApiOperation("이름으로 회원 선물 조회")
    @GetMapping("/gift-by-name/{userNo}/{giftName}")
    public List<GiftDetailResponseDto> readGiftListByname (@PathVariable("userNo") Long userNo, @PathVariable("giftName") String giftName){
        return userService.readGiftListByName(userNo, giftName);
    }

    @ApiOperation("카테고리로 회원 선물 조회")
    @GetMapping("/gift-by-category/{userNo}/{category}")
    public List<GiftDetailResponseDto> readGiftListByCategory(@PathVariable("userNo") Long userNo, @PathVariable("category") Category category){
        return userService.readGiftListByCategory(userNo, category);
    }

    @ApiOperation("감정으로 회원 선물 검색")
    @GetMapping("/gift-by-emotion/{userNo}/{emotion}")
    public List<GiftDetailResponseDto> readGiftListByEmotion(@PathVariable("userNo") Long userNo, @PathVariable("emotion") Emotion emotion){
        return userService.readGiftListByEmotion(userNo, emotion);
    }

    @ApiOperation("아이디로 회원 조회")
    @GetMapping("id/{userId}")
    public UserResponseDto readUserByUserId(@PathVariable("userId") String userId){
        return userService.findUserByUserId(userId);
    }

}
