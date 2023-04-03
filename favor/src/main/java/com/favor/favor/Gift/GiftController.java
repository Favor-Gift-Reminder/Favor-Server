package com.favor.favor.Gift;

import com.favor.favor.Common.DefaultResponseDto;
import com.favor.favor.Friend.Friend;
import com.favor.favor.Friend.FriendResponseDto;
import com.favor.favor.User.UserResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@Api(tags = "Gift")
@RestController
@RequestMapping("/gifts")
@RequiredArgsConstructor
@Log4j2
public class GiftController {
    private final GiftService giftService;

    @ApiOperation("선물 생성")
    @ApiResponses(value={
            @ApiResponse(code = 201,
                    message = "GIFT_CREATED",
                    response = GiftResponseDto.class),
            @ApiResponse(code = 400,
                    message = "FILED_REQUIRED / *_CHARACTER_INVALID / *_LENGTH_INVALID"),
            @ApiResponse(code = 404,
                    message = "USER_NOT_FOUND"),
            @ApiResponse(code = 500,
                    message = "SERVER_ERROR")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{userNo}/{friendNo}")
    public ResponseEntity<DefaultResponseDto<Object>> createGift(
            @RequestBody GiftRequestDto giftRequestDto,
            @PathVariable("userNo") Long userNo,
            @PathVariable("friendNo") Long friendNo){

        giftService.isExistingUserNo(userNo);
        giftService.isExistingFriendNo(friendNo);

        Gift gift = giftService.createGift(giftRequestDto, userNo, friendNo);
        GiftResponseDto dto = giftService.returnDto(gift);

        return ResponseEntity.status(201)
                .body(DefaultResponseDto.builder()
                        .responseCode("GIFT_CREATED")
                        .responseMessage("선물 생성 완료")
                        .data(dto)
                        .build());
    }

    @ApiOperation("선물 조회")
    @ApiResponses(value={
            @ApiResponse(code = 200,
                    message = "GIFT_FOUND",
                    response = FriendResponseDto.class),
            @ApiResponse(code = 401,
                    message = "UNAUTHORIZED_USER"),
            @ApiResponse(code = 404,
                    message = "GIFT_NOT_FOUND / USER_NOT_FOUND"),
            @ApiResponse(code = 500,
                    message = "SERVER_ERROR")
    })
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    @GetMapping("/{giftNo}")
    public ResponseEntity<DefaultResponseDto<Object>> readGift(
            @PathVariable Long giftNo){

        giftService.isExistingGiftNo(giftNo);
        Gift gift = giftService.findGiftByGiftNo(giftNo);
        GiftResponseDto dto = giftService.returnDto(gift);

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("GIFT_FOUND")
                        .responseMessage("선물 조회 완료")
                        .data(dto)
                        .build());
    }

    @ApiOperation("선물 수정")
    @ApiResponses(value={
            @ApiResponse(code = 200,
                    message = "GIFT_UPDATED",
                    response = FriendResponseDto.class),
            @ApiResponse(code = 401,
                    message = "UNAUTHORIZED_USER"),
            @ApiResponse(code = 404,
                    message = "GIFT_NOT_FOUND / FRIEND_NOT_FOUND"),
            @ApiResponse(code = 500,
                    message = "SERVER_ERROR")
    })
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    @PatchMapping("/{giftNo}")
    public ResponseEntity<DefaultResponseDto<Object>> updateGift(
            @RequestBody GiftUpdateRequestDto giftUpdateRequestDto,
            @PathVariable Long giftNo, Long friendNo){

        giftService.isExistingGiftNo(giftNo);
        giftService.isExistingFriendNo(friendNo);

        Gift gift = giftService.findGiftByGiftNo(giftNo);
        giftService.updateGift(giftUpdateRequestDto, gift, friendNo);

        GiftResponseDto dto = giftService.returnDto(gift);

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("GIFT_UPDATED")
                        .responseMessage("선물 수정 완료")
                        .data(dto)
                        .build());
    }

    @ApiOperation("선물 삭제")
    @ApiResponses(value={
            @ApiResponse(code = 200,
                    message = "GIFT_DELETED",
                    response = UserResponseDto.class),
            @ApiResponse(code = 401,
                    message = "UNAUTHORIZED_USER"),
            @ApiResponse(code = 404,
                    message = "GIFT_NOT_FOUND"),
            @ApiResponse(code = 500,
                    message = "SERVER_ERROR")
    })
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    @DeleteMapping("/{giftNo}")
    public ResponseEntity<DefaultResponseDto<Object>> deleteGift(
            @PathVariable Long giftNo){

        giftService.isExistingGiftNo(giftNo);

        Gift gift = giftService.findGiftByGiftNo(giftNo);
        GiftResponseDto dto = giftService.returnDto(gift);

        giftService.deleteGift(giftNo);

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("GIFT_DELETED")
                        .responseMessage("선물 삭제 완료")
                        .data(dto)
                        .build());
    }

    @ApiOperation("전체 선물 조회")
    @ApiResponses(value={
            @ApiResponse(code = 200,
                    message = "GIFTS_FOUND",
                    response = UserResponseDto.class),
            @ApiResponse(code = 401,
                    message = "UNAUTHORIZED_USER"),
            @ApiResponse(code = 500,
                    message = "SERVER_ERROR")
    })
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    @GetMapping
    public ResponseEntity<DefaultResponseDto<Object>> readAll(){

        List<GiftResponseDto> dto = giftService.readAll();

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("GIFTS_FOUND")
                        .responseMessage("전체 선물 조회 완료")
                        .data(dto)
                        .build());

    }
}
