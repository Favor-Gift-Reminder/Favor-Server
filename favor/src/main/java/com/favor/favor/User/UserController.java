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
        UserDetailResponseDto dto = new UserDetailResponseDto(user);

        return ResponseEntity.status(201)
                .body(DefaultResponseDto.builder()
                        .responseCode("USER_REGISTERED")
                        .responseMessage("USER 회원가입 완료")
                        .data(dto)
                        .build());
    }

    @ApiOperation("프로필생성")
    @PatchMapping("/profile")
    public UserDetailResponseDto createProfile(@RequestBody ProfileDto profileDto, Long userNo) {
        return userService.createProfile(profileDto, userNo);
    }

    @ApiOperation("단일 회원 조회")
    @GetMapping("/{userNo}")
    public UserDetailResponseDto readUser(@PathVariable Long userNo){
        UserDetailResponseDto foundUser = userService.readUser(userNo);
        return foundUser;
    }

    @ApiOperation("회원 수정")
    @PatchMapping("/{userNo}")
    public UserDetailResponseDto updateUser(@PathVariable Long userNo, @RequestBody UserUpdateRequestDto userUpdateRequestDto){
        return userService.updateUser(userNo, userUpdateRequestDto);
    }

    @ApiOperation("회원 탈퇴")
    @DeleteMapping("/{userNo}")
    public UserDetailResponseDto deleteUser(@PathVariable Long userNo){
        return userService.deleteUser(userNo);
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
        return userService.readUserByUserId(userId);
    }

}
