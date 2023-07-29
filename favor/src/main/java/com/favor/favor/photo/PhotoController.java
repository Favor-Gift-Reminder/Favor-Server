package com.favor.favor.photo;

import com.favor.favor.common.DefaultResponseDto;
import com.favor.favor.gift.GiftResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "Photo")
@RestController
@RequiredArgsConstructor
@RequestMapping("/photos")
public class PhotoController {
    private final PhotoService photoService;

    @ApiOperation("사진 저장")
    @ApiResponses(value={
            @ApiResponse(code = 201,
                    message = "PHOTO_SAVED",
                    response = GiftResponseDto.class),
            @ApiResponse(code = 400,
                    message = "FIELD_REQUIRED / *_CHARACTER_INVALID / *_LENGTH_INVALID"),
            @ApiResponse(code = 404,
                    message = ""),
            @ApiResponse(code = 500,
                    message = "SERVER_ERROR")
    })
    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    public ResponseEntity<DefaultResponseDto<Object>> savePhoto(
            @ModelAttribute MultipartFile photo){

        Photo result = photoService.savePhoto(photo);

        return ResponseEntity.status(201)
                .body(DefaultResponseDto.builder()
                        .responseCode("PHOTO_SAVED")
                        .responseMessage("사진 저장 완료")
                        .data(result)
                        .build());
    }

    @ApiOperation("사진 삭제")
    @ApiResponses(value={
            @ApiResponse(code = 201,
                    message = "PHOTO_REMOVED",
                    response = GiftResponseDto.class),
            @ApiResponse(code = 400,
                    message = "FIELD_REQUIRED / *_CHARACTER_INVALID / *_LENGTH_INVALID"),
            @ApiResponse(code = 404,
                    message = ""),
            @ApiResponse(code = 500,
                    message = "SERVER_ERROR")
    })
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping
    public ResponseEntity<DefaultResponseDto<Object>> deletePhoto(
            String filename){

        photoService.deleteFileFromS3(filename);

        return ResponseEntity.status(201)
                .body(DefaultResponseDto.builder()
                        .responseCode("PHOTO_REMOVED")
                        .responseMessage("사진 삭제 완료")
                        .data(null)
                        .build());
    }
}
