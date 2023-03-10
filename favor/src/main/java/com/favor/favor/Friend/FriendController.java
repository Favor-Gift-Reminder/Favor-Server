package com.favor.favor.Friend;

import com.favor.favor.Friend.Account.UserFriendRequestDto;
import com.favor.favor.Friend.NoAccount.FriendRequestDto;
import com.favor.favor.Friend.NoAccount.FriendUpdateRequestDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Friend")
@RestController
@RequestMapping("/friends")
@RequiredArgsConstructor
public class FriendController {
    private final FriendService friendService;

    @ApiOperation("친구 생성")
    @PostMapping("/{userNo}")
    public FriendDetailResponseDto createFriend(@RequestBody FriendRequestDto dto, @PathVariable Long userNo){
        return friendService.createFriend(dto, userNo);
    }
    @ApiOperation("회원친구 추가")
    @PostMapping("/add/{userNo}")
    public FriendDetailResponseDto addFriend(@RequestBody UserFriendRequestDto dto, @PathVariable Long userNo){
        return friendService.addFriend(dto, userNo);
    }

    @ApiOperation("단일 친구 조회")
    @GetMapping("/{friendNo}")
    public FriendDetailResponseDto readFriend(@PathVariable Long friendNo){
        return friendService.readFriend(friendNo);
    }

    @ApiOperation("친구 수정")
    @PatchMapping("/{friendNo}")
    public FriendDetailResponseDto updateFriend(@PathVariable Long friendNo, @RequestBody FriendUpdateRequestDto dto){
        return friendService.updateFriend(friendNo, dto);
    }

    @ApiOperation("친구 삭제")
    @DeleteMapping("/{friendNo}")
    public FriendDetailResponseDto deleteFriend(@PathVariable Long friendNo){
        return friendService.deleteFriend(friendNo);
    }

    @ApiOperation("전체 친구 조회")
    @GetMapping
    public List<FriendResponseDto> readAll(){
        return friendService.readAll();
    }
}
