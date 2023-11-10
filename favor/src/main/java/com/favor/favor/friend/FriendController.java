package com.favor.favor.friend;

import com.favor.favor.common.DefaultResponseDto;

import com.favor.favor.gift.GiftResponseDto;
import com.favor.favor.gift.GiftSimpleDto;
import com.favor.favor.user.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.favor.favor.common.DefaultResponseDto.resWithData;
import static com.favor.favor.common.DefaultResponseDto.resWithoutData;


@Api(tags = "Friend")
@RestController
@RequestMapping("/friends")
@RequiredArgsConstructor
public class FriendController {
    private final FriendService friendService;

    @ApiOperation("친구 추가")
    @ApiResponses(value={
            @ApiResponse(code = 201,
                    message = "FRIEND_ADDED",
                    response = FriendResponseDto.class),
            @ApiResponse(code = 400,
                    message = "FIELD_REQUIRED / *_CHARACTER_INVALID / *_LENGTH_INVALID"),
            @ApiResponse(code = 404,
                    message = "USER_NOT_FOUND"),
            @ApiResponse(code = 500,
                    message = "SERVER_ERROR")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<DefaultResponseDto<Object>> addFriend(
            @RequestBody FriendRequestDto friendRequestDto,
            @AuthenticationPrincipal User loginUser){

        Long userNo = loginUser.getUserNo();

        friendService.isExistingUserNo(userNo);
        friendService.isExistingFriendUserNo(friendRequestDto.getFriendUserNo());

        Friend friend = friendService.addFriend(friendRequestDto, userNo);
        FriendResponseDto dto = friendService.returnDto(friend);

        return ResponseEntity.status(201)
                .body(resWithData("FRIEND_ADDED", "친구 추가 완료", dto));
    }

    @ApiOperation("친구 조회")
    @ApiResponses(value={
            @ApiResponse(code = 200,
                    message = "FRIEND_FOUND",
                    response = FriendResponseDto.class),
            @ApiResponse(code = 401,
                    message = "UNAUTHORIZED_USER"),
            @ApiResponse(code = 404,
                    message = "FRIEND_NOT_FOUND"),
            @ApiResponse(code = 500,
                    message = "SERVER_ERROR")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{friendNo}")
    public ResponseEntity<DefaultResponseDto<Object>> readFriend(
            @PathVariable Long friendNo){

        friendService.isExistingFriendNo(friendNo);

        Friend friend = friendService.findFriendByFriendNo(friendNo);
        FriendResponseDto dto = friendService.returnDto(friend);

        return ResponseEntity.status(200)
                .body(resWithData("FRIEND_FOUND", "친구 조회 완료", dto));
    }

    @ApiOperation("친구 메모 수정")
    @ApiResponses(value={
            @ApiResponse(code = 200,
                    message = "FRIEND_MEMO_UPDATED",
                    response = FriendResponseDto.class),
            @ApiResponse(code = 401,
                    message = "UNAUTHORIZED_USER"),
            @ApiResponse(code = 404,
                    message = "FRIEND_NOT_FOUND"),
            @ApiResponse(code = 500,
                    message = "SERVER_ERROR")
    })
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{friendNo}")
    public ResponseEntity<DefaultResponseDto<Object>> updateFriend(
            @PathVariable Long friendNo,
            @RequestBody MemoUpdateRequestDto memoUpdateRequestDto){

        friendService.isExistingFriendNo(friendNo);

        Friend friend = friendService.findFriendByFriendNo(friendNo);

        friendService.updateMemo(friend, memoUpdateRequestDto);

        FriendResponseDto dto = friendService.returnDto(friend);

        return ResponseEntity.status(200)
                .body(resWithData("FRIEND_MEMO_UPDATED", "친구 메모 수정 완료", dto));
    }

    @ApiOperation("친구 삭제")
    @ApiResponses(value={
            @ApiResponse(code = 200,
                    message = "FRIEND_DELETED",
                    response = FriendResponseDto.class),
            @ApiResponse(code = 401,
                    message = "UNAUTHORIZED_USER"),
            @ApiResponse(code = 404,
                    message = "FRIEND_NOT_FOUND"),
            @ApiResponse(code = 500,
                    message = "SERVER_ERROR")
    })
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{friendNo}")
    public ResponseEntity<DefaultResponseDto<Object>> deleteFriend(
            @PathVariable Long friendNo){
        friendService.isExistingFriendNo(friendNo);

        Friend friend = friendService.findFriendByFriendNo(friendNo);
        FriendResponseDto dto = friendService.returnDto(friend);

        friendService.deleteFriend(friendNo);

        return ResponseEntity.status(200)
                .body(resWithoutData("FRIEND_DELETED", "친구 삭제 완료"));
    }

    @ApiOperation("전체 친구 조회")
    @ApiResponses(value={
            @ApiResponse(code = 200,
                    message = "FRIENDS_FOUND",
                    response = FriendResponseDto.class),
            @ApiResponse(code = 401,
                    message = "UNAUTHORIZED_USER"),
            @ApiResponse(code = 500,
                    message = "SERVER_ERROR")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/admin")
    public ResponseEntity<DefaultResponseDto<Object>> readAll(){

        List<FriendResponseDto> dto = friendService.readAll();

        return ResponseEntity.status(200)
                .body(resWithData("FRIENDS_FOUND", "전체 친구 조회 완료", dto));
    }

    @ApiOperation("친구의 선물 전체 조회")
    @ApiResponses(value={
            @ApiResponse(code = 200,
                    message = "GIFTS_FOUND",
                    response = GiftResponseDto.class),
            @ApiResponse(code = 401,
                    message = "UNAUTHORIZED_USER"),
            @ApiResponse(code = 500,
                    message = "SERVER_ERROR")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/total-gifts/{friendNo}")
    public ResponseEntity<DefaultResponseDto<Object>> readTotalGiftList(
            @PathVariable Long friendNo){
        friendService.isExistingFriendNo(friendNo);
        List<GiftSimpleDto> dto = friendService.findGiftListByFriendNo(friendNo);

        return ResponseEntity.status(200)
                .body(resWithData("GIFTS_FOUND", "친구의 선물 전체 조회 완료", dto));
    }

    @ApiOperation("친구가 준 선물 전체 조회")
    @ApiResponses(value={
            @ApiResponse(code = 200,
                    message = "GIFTS_FOUND",
                    response = GiftResponseDto.class),
            @ApiResponse(code = 401,
                    message = "UNAUTHORIZED_USER"),
            @ApiResponse(code = 500,
                    message = "SERVER_ERROR")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/given-gifts/{friendNo}")
    public ResponseEntity<DefaultResponseDto<Object>> readGivenGiftList(
            @PathVariable Long friendNo){ // 유저 입장에서 받은 선물이므로 관련 친구가 준 선물임
        friendService.isExistingFriendNo(friendNo);
        List<GiftSimpleDto> dto = friendService.findReceivedGiftList(friendNo);

        return ResponseEntity.status(200)
                .body(resWithData("GIFTS_FOUND", "친구가 준 선물 전체 조회 완료", dto));
    }

    @ApiOperation("친구가 받은 선물 전체 조회")
    @ApiResponses(value={
            @ApiResponse(code = 200,
                    message = "FRIENDS_FOUND",
                    response = GiftResponseDto.class),
            @ApiResponse(code = 401,
                    message = "UNAUTHORIZED_USER"),
            @ApiResponse(code = 500,
                    message = "SERVER_ERROR")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/received-gifts/{friendNo}")
    public ResponseEntity<DefaultResponseDto<Object>> readReceivedGiftList(
            @PathVariable Long friendNo){// 유저 입장에서 준 선물이므로 관련 친구가 받은 선물임
        friendService.isExistingFriendNo(friendNo);
        List<GiftSimpleDto> dto = friendService.findGivenGiftList(friendNo);

        return ResponseEntity.status(200)
                .body(resWithData("GIFTS_FOUND", "친구가 받은 선물 전체 조회 완료", dto));
    }
}
