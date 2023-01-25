package com.favor.favor.Friend;

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
    public Long createFriend

    @ApiOperation("친구조회")
    @GetMapping("/{friendNo}")

    @ApiOperation("친구수정")
    @PatchMapping("/{friendNo}")

    @ApiOperation("친구삭제")
    @PatchMapping("/{friendNo}")
}
