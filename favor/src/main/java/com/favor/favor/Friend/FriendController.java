package com.favor.favor.Friend;

import com.favor.favor.Friend.Account.UserFriendResponseDto;
import com.favor.favor.Friend.NoAccount.FriendResponseDto;
import com.favor.favor.Friend.NoAccount.FriendUpdateRequestDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api
@RestController
@RequestMapping("/friends")
@RequiredArgsConstructor
public class FriendController {
    private final FriendService friendService;

    @ApiOperation("친구추가")
    @PostMapping
    public Long createFriend(@RequestBody FriendRequestDto dto){
        return friendService.createFriend(dto);
    }

    @ApiOperation("친구조회")
    @GetMapping("/{friendNo}")
    public FriendResponseDto readFriend(@PathVariable Long friendNo){
        return friendService.readFriend(friendNo);
    }

    @ApiOperation("유저친구조회")
    @GetMapping("/{friendNo}")
    public UserFriendResponseDto readUserFriend(@PathVariable Long friendNo){
        return friendService.readUserFriend(friendNo);
    }

    @ApiOperation("친구수정")
    @PatchMapping("/{friendNo}")
    public Long updateFriend(@PathVariable Long friendNo, @RequestBody FriendUpdateRequestDto dto){
        return friendService.updateFriend(friendNo, dto);
    }

    @ApiOperation("친구삭제")
    @PatchMapping("/{friendNo}")
    public Long deleteFriend(@PathVariable Long friendNo){
        return friendService.deleteFriend(friendNo);
    }
}
