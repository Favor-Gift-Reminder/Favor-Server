package com.favor.favor.gift;

import com.favor.favor.common.DefaultResponseDto;
import com.favor.favor.photo.GiftPhoto;
import com.favor.favor.photo.UserPhoto;
import com.favor.favor.user.User;
import com.favor.favor.user.UserPhotoService;
import com.favor.favor.user.UserResponseDto;
import com.favor.favor.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;

@Api(tags = "Gift-Photo")
@RestController
@RequestMapping("/giftphotos")
@RequiredArgsConstructor
public class GiftPhotoController {
    private final GiftPhotoService giftPhotoService;
    private final GiftService giftService;

    @ApiOperation(value = "선물 사진 추가")
    @ApiResponses(value={
            @ApiResponse(code = 201,
                    message = "GIFT_PHOTO_LIST_ADDED",
                    response = UserResponseDto.class),
            @ApiResponse(code = 400,
                    message = "FIELD_REQUIRED / *_CHARACTER_INVALID / *_LENGTH_INVALID"),
            @ApiResponse(code = 401,
                    message = "UNAUTHORIZED_USER"),
            @ApiResponse(code = 404,
                    message = "GIFT_NOT_FOUND"),
            @ApiResponse(code = 500,
                    message = "SERVER_ERROR")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<DefaultResponseDto<Object>> addGiftPhotoList(
            @ModelAttribute MultipartFile file,
            Long giftNo
    ) {
        giftService.isExistingGiftNo(giftNo);

        Gift gift = giftPhotoService.addGiftPhoto(giftNo, file);

        GiftResponseDto dto = giftService.returnDto(gift);

        return ResponseEntity.status(201)
                .body(DefaultResponseDto.builder()
                        .responseCode("GIFT_PHOTO_LIST_ADDED")
                        .responseMessage("선물 사진 추가")
                        .data(dto)
                        .build());
    }

    @ApiOperation("선물 사진 목록 조회")
    @ApiResponses(value={
            @ApiResponse(code = 200,
                    message = "GIFT_PHOTO_LIST_FOUND",
                    response = UserResponseDto.class),
            @ApiResponse(code = 401,
                    message = "UNAUTHORIZED_USER"),
            @ApiResponse(code = 404,
                    message = "GIFT_NOT_FOUND | FILE_NOT_FOUND"),
            @ApiResponse(code = 500,
                    message = "SERVER_ERROR")
    })
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    @GetMapping
    public ResponseEntity<DefaultResponseDto<Object>> getUserProfilePhoto(Long giftNo) {
        Gift gift = giftService.findGiftByGiftNo(giftNo);

        List<GiftPhoto> dto = giftPhotoService.getGiftPhotoList(giftNo);

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("GIFT_PHOTO_LIST_FOUND")
                        .responseMessage("선물 사진 목록 조회 완료")
                        .data(dto)
                        .build());
    }

    @ApiOperation("선물 사진 삭제")
    @ApiResponses(value={
            @ApiResponse(code = 200,
                    message = "GIFT_PHOTO_DELETED",
                    response = UserResponseDto.class),
            @ApiResponse(code = 401,
                    message = "UNAUTHORIZED_USER"),
            @ApiResponse(code = 404,
                    message = "GIFT_NOT_FOUND | FILE_NOT_FOUND"),
            @ApiResponse(code = 500,
                    message = "SERVER_ERROR")
    })
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    @DeleteMapping
    public ResponseEntity<DefaultResponseDto<Object>> deleteUserProfilePhoto(
            Long giftNo, String fileUrl) {

        Gift gift = giftPhotoService.deleteGiftPhoto(giftNo, fileUrl);
        GiftResponseDto dto = new GiftResponseDto(gift);

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("GIFT_PHOTO_DELETED")
                        .responseMessage("선물 사진 삭제 완료")
                        .data(dto)
                        .build());
    }
}
