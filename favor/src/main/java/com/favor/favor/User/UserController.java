package com.favor.favor.User;


import com.favor.favor.Common.Category;
import com.favor.favor.Common.Emotion;
import com.favor.favor.Friend.FriendResponseDto;
import com.favor.favor.Gift.GiftDetailResponseDto;
import com.favor.favor.Gift.GiftResponseDto;
import com.favor.favor.Reminder.ReminderResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "User")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @ApiOperation("회원가입")
    @PostMapping("/sign-up")
    public UserDetailResponseDto signUp(@RequestBody UserRequestDto userRequestDto) {
        return userService.signUp(userRequestDto);
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
    public UserResponseDto deleteUser(@PathVariable Long userNo){
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
