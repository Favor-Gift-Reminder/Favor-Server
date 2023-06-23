package com.favor.favor.friend;

import com.favor.favor.common.DefaultResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

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
                    message = "FILED_REQUIRED / *_CHARACTER_INVALID / *_LENGTH_INVALID"),
            @ApiResponse(code = 404,
                    message = "USER_NOT_FOUND"),
            @ApiResponse(code = 500,
                    message = "SERVER_ERROR")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/add/{userNo}")
    public ResponseEntity<DefaultResponseDto<Object>> addFriend(
            @RequestBody FriendRequestDto friendRequestDto,
            @PathVariable Long userNo){

        friendService.isExistingUserNo(userNo);
        friendService.isExistingFriendUserNo(friendRequestDto.getFriendUserNo());

        Friend friend = friendService.addFriend(friendRequestDto, userNo);
        FriendResponseDto dto = friendService.returnDto(friend);

        return ResponseEntity.status(201)
                .body(DefaultResponseDto.builder()
                        .responseCode("FRIEND_ADDED")
                        .responseMessage("친구 추가 완료")
                        .data(dto)
                        .build());
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
    @Transactional
    @GetMapping("/{friendNo}")
    public ResponseEntity<DefaultResponseDto<Object>> readFriend(
            @PathVariable Long friendNo){

        friendService.isExistingFriendNo(friendNo);

        Friend friend = friendService.findFriendByFriendNo(friendNo);
        FriendResponseDto dto = friendService.returnDto(friend);

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("FRIEND_FOUND")
                        .responseMessage("친구 조회 완료")
                        .data(dto)
                        .build());
    }

//    @ApiOperation("친구 수정")
//    @ApiResponses(value={
//            @ApiResponse(code = 200,
//                    message = "FRIEND_UPDATED",
//                    response = FriendResponseDto.class),
//            @ApiResponse(code = 401,
//                    message = "UNAUTHORIZED_USER"),
//            @ApiResponse(code = 404,
//                    message = "FRIEND_NOT_FOUND"),
//            @ApiResponse(code = 500,
//                    message = "SERVER_ERROR")
//    })
//    @ResponseStatus(HttpStatus.OK)
//    @Transactional
//    @PatchMapping("/{friendNo}")
//    public ResponseEntity<DefaultResponseDto<Object>> updateFriend(
//            @PathVariable Long friendNo,
//            @RequestBody FriendUpdateRequestDto friendUpdateRequestDto){
//
//        friendService.isExistingFriendNo(friendNo);
//
//        Friend friend = friendService.findFriendByFriendNo(friendNo);
//        friendService.updateFriend(friend, friendUpdateRequestDto);
//
//        FriendResponseDto dto = friendService.returnDto(friend);
//
//        return ResponseEntity.status(200)
//                .body(DefaultResponseDto.builder()
//                        .responseCode("FRIEND_UPDATED")
//                        .responseMessage("친구 수정 완료")
//                        .data(dto)
//                        .build());
//    }

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
    @Transactional
    @DeleteMapping("/{friendNo}")
    public ResponseEntity<DefaultResponseDto<Object>> deleteFriend(
            @PathVariable Long friendNo){
        friendService.isExistingFriendNo(friendNo);

        Friend friend = friendService.findFriendByFriendNo(friendNo);
        FriendResponseDto dto = friendService.returnDto(friend);

        friendService.deleteFriend(friendNo);

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("FRIEND_DELETED")
                        .responseMessage("친구 삭제 완료")
                        .data(dto)
                        .build());
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
    @Transactional
    @GetMapping
    public ResponseEntity<DefaultResponseDto<Object>> readAll(){

        List<FriendResponseDto> dto = friendService.readAll();

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("FRIENDS_FOUND")
                        .responseMessage("전체 친구 조회 완료")
                        .data(dto)
                        .build());
    }
}
