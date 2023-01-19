package com.favor.favor.User;


import com.favor.favor.Reminder.ReminderListResponseDto;
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
    public void signUp(@RequestBody SignUpRequestDto signUpRequestDto){
        userService.signUp(signUpRequestDto);
    }

    @ApiOperation("전체회원 조회")
    @GetMapping
    public List<UserResponseDto> readAll(){
        return userService.readAll();
    }

    @ApiOperation("단일회원 조회")
    @GetMapping("/{userNo}")
    public UserResponseDto readUser(@PathVariable Long userNo){
        UserResponseDto foundUser = userService.readUser(userNo);
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
    public List<ReminderListResponseDto> readReminderList(@PathVariable Long userNo){
        return userService.readReminderList(userNo);
    }
}
