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

import java.io.File;
import java.util.List;

@Api(tags = "Photo")
@RestController
@RequiredArgsConstructor
@RequestMapping("/photos")
public class PhotoController {

    private final S3Service s3Service;

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
            @ModelAttribute PhotoDto photoDto){

        StringBuffer sb = new StringBuffer();
        List<File> fileList = photoDto.getFileList();
        for(File file : fileList){
            s3Service.uploadPhoto(file);
        }

        String result = sb.toString();

        return ResponseEntity.status(201)
                .body(DefaultResponseDto.builder()
                        .responseCode("PHOTO_SAVED")
                        .responseMessage("사진 저장 완료")
                        .data(result)
                        .build());
    }
}
