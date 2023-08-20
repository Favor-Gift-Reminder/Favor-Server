package com.favor.favor.gift;

import com.favor.favor.common.DefaultResponseDto;
import com.favor.favor.user.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@Api(tags = "Gift")
@RestController
@RequestMapping("/gifts")
@RequiredArgsConstructor
@Slf4j
public class GiftController {
    private final GiftService giftService;

    @ApiOperation("선물 생성")
    @ApiResponses(value={
            @ApiResponse(code = 201,
                    message = "GIFT_CREATED",
                    response = GiftResponseDto.class),
            @ApiResponse(code = 400,
                    message = "FIELD_REQUIRED / *_CHARACTER_INVALID / *_LENGTH_INVALID"),
            @ApiResponse(code = 404,
                    message = "USER_NOT_FOUND / FREIND_NOT_FOUND"),
            @ApiResponse(code = 500,
                    message = "SERVER_ERROR")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<DefaultResponseDto<Object>> createGift(
            @RequestBody GiftRequestDto giftRequestDto,
            @AuthenticationPrincipal User loginUser){

        Long userNo = loginUser.getUserNo();

        giftService.isExistingUserNo(userNo);
        for(Long friendNo : giftRequestDto.getFriendNoList()){
            giftService.isExistingFriendNo(friendNo);
        }

        Gift gift = giftService.createGift(giftRequestDto, userNo);
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
                    response = GiftResponseDto.class),
            @ApiResponse(code = 401,
                    message = "UNAUTHORIZED_USER"),
            @ApiResponse(code = 404,
                    message = "GIFT_NOT_FOUND / USER_NOT_FOUND"),
            @ApiResponse(code = 500,
                    message = "SERVER_ERROR")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{giftNo}")
    public ResponseEntity<DefaultResponseDto<Object>> readGift(
            @PathVariable Long giftNo){

        giftService.isExistingGiftNo(giftNo);
        Gift gift = giftService.findGiftByGiftNo(giftNo);
        log.info("[Controller] [readGift] 선물 찾음");
        GiftResponseDto dto = giftService.returnDto(gift);
        log.info("[Controller] [readGift] DTO 반환");

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
                    response = GiftResponseDto.class),
            @ApiResponse(code = 401,
                    message = "UNAUTHORIZED_USER"),
            @ApiResponse(code = 404,
                    message = "GIFT_NOT_FOUND / FRIEND_NOT_FOUND"),
            @ApiResponse(code = 500,
                    message = "SERVER_ERROR")
    })
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{giftNo}")
    public ResponseEntity<DefaultResponseDto<Object>> updateGift(
            @RequestBody GiftUpdateRequestDto giftUpdateRequestDto,
            @PathVariable Long giftNo){

        giftService.isExistingGiftNo(giftNo);


        Gift gift = giftService.findGiftByGiftNo(giftNo);
        giftService.updateGift(giftUpdateRequestDto, gift);
        GiftResponseDto dto = giftService.returnDto(gift);

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("GIFT_UPDATED")
                        .responseMessage("선물 수정 완료")
                        .data(dto)
                        .build());
    }

    @ApiOperation("선물 임시친구목록 수정")
    @ApiResponses(value={
            @ApiResponse(code = 200,
                    message = "GIFT_TEMP_FRIEND_LIST_UPDATED",
                    response = GiftResponseDto.class),
            @ApiResponse(code = 401,
                    message = "UNAUTHORIZED_USER"),
            @ApiResponse(code = 404,
                    message = "GIFT_NOT_FOUND / FRIEND_NOT_FOUND"),
            @ApiResponse(code = 500,
                    message = "SERVER_ERROR")
    })
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/temp-friend-list/{giftNo}")
    public ResponseEntity<DefaultResponseDto<Object>> updateTempFriendListGift(
            @PathVariable Long giftNo,
            @RequestBody GiftTempFriendListDto tempFriendListDto
            ){

        giftService.isExistingGiftNo(giftNo);


        Gift gift = giftService.findGiftByGiftNo(giftNo);
        giftService.updateTempFriendList(gift, tempFriendListDto);
        GiftResponseDto dto = giftService.returnDto(gift);

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("GIFT_TEMP_FRIEND_LIST_UPDATED")
                        .responseMessage("선물 임시친구목록 수정 완료")
                        .data(dto)
                        .build());
    }

    @ApiOperation("선물 핀 여부 수정")
    @ApiResponses(value={
            @ApiResponse(code = 200,
                    message = "GIFT_PIN_UPDATED",
                    response = GiftResponseDto.class),
            @ApiResponse(code = 401,
                    message = "UNAUTHORIZED_USER"),
            @ApiResponse(code = 404,
                    message = "GIFT_NOT_FOUND / FRIEND_NOT_FOUND"),
            @ApiResponse(code = 500,
                    message = "SERVER_ERROR")
    })
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/pin/{giftNo}")
    public ResponseEntity<DefaultResponseDto<Object>> updateIsPinned(
            @PathVariable Long giftNo){

        giftService.isExistingGiftNo(giftNo);


        Gift gift = giftService.findGiftByGiftNo(giftNo);
        giftService.updateIsPinned(gift);
        GiftResponseDto dto = giftService.returnDto(gift);

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("GIFT_PIN_UPDATED")
                        .responseMessage("선물 핀 여부 수정 완료")
                        .data(dto)
                        .build());
    }

    @ApiOperation("선물 삭제")
    @ApiResponses(value={
            @ApiResponse(code = 200,
                    message = "GIFT_DELETED",
                    response = GiftResponseDto.class),
            @ApiResponse(code = 401,
                    message = "UNAUTHORIZED_USER"),
            @ApiResponse(code = 404,
                    message = "GIFT_NOT_FOUND"),
            @ApiResponse(code = 500,
                    message = "SERVER_ERROR")
    })
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{giftNo}")
    public ResponseEntity<DefaultResponseDto<Object>> deleteGift(
            @PathVariable Long giftNo){

        giftService.isExistingGiftNo(giftNo);

        Gift gift = giftService.findGiftByGiftNo(giftNo);
        log.info("[SYSTEM] giftService.findGiftByGiftNo(giftNo) 완료");
        log.info("[SYSTEM] gift = {}", gift);

        GiftResponseDto dto = giftService.returnDto(gift);
        log.info("[SYSTEM] giftService.returnDto(gift) 완료");
        log.info("[SYSTEM] dto = {}", dto);

        giftService.deleteGift(giftNo);
        log.info("[SYSTEM] giftService.deleteGift(giftNo) 완료");

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
                    response = GiftResponseDto.class),
            @ApiResponse(code = 401,
                    message = "UNAUTHORIZED_USER"),
            @ApiResponse(code = 500,
                    message = "SERVER_ERROR")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/admin")
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
