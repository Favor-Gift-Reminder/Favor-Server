package com.favor.favor.User;


import com.favor.favor.Friend.FriendListResponseDto;
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
    public Long signUp(@RequestBody UserRequestDto userRequestDto){
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
    public Long updateUser(@PathVariable Long userNo, @RequestBody UserUpdateRequestDto userUpdateRequestDto){
        userService.updateUser(userNo, userUpdateRequestDto);
        return userNo;
    }

    @ApiOperation("회원 탈퇴")
    @DeleteMapping("/{userNo}")
    public Long deleteUser(@PathVariable Long userNo){
        userService.deleteUser(userNo);
        return userNo;
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
     public List<FriendListResponseDto> readFriendList(@PathVariable Long userNo){
        return userService.readFriendList(userNo);
     }


    @ApiOperation("전체 회원 조회")
    @GetMapping
    public List<UserResponseDto> readAll(){
        return userService.readAll();
    }
}
