package com.favor.favor.anniversary;

import com.favor.favor.common.DefaultResponseDto;
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

@Api(tags = "Anniversary")
@RestController
@RequestMapping("/anniversaries")
@RequiredArgsConstructor
public class AnniversaryController {

    private final AnniversaryService anniversaryService;

    @ApiOperation("기념일 생성")
    @ApiResponses(value={
            @ApiResponse(code = 201,
                    message = "REMINDER_CREATED",
                    response = AnniversaryResponseDto.class),
            @ApiResponse(code = 400,
                    message = "FIELD_REQUIRED / *_CHARACTER_INVALID / *_LENGTH_INVALID"),
            @ApiResponse(code = 404,
                    message = "USER_NOT_FOUND / FRIEND_NOT_FOUND"),
            @ApiResponse(code = 500,
                    message = "SERVER_ERROR")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<DefaultResponseDto<Object>> createAnniversary(
            @RequestBody AnniversaryRequestDto anniversaryRequestDto,
            @AuthenticationPrincipal User loginUser){

        Long userNo = loginUser.getUserNo();

        anniversaryService.isExistingUserNo(userNo);

        Anniversary anniversary = anniversaryService.createAnniversary(anniversaryRequestDto, userNo);
        AnniversaryResponseDto dto = anniversaryService.returnDto(anniversary);

        return ResponseEntity.status(201)
                .body(resWithData("ANIVERSARY_CREATED", "기념일 생성 완료", dto));
    }

    @ApiOperation("기념일 조회")
    @ApiResponses(value={
            @ApiResponse(code = 200,
                    message = "ANNIVERSARY_FOUND",
                    response = AnniversaryResponseDto.class),
            @ApiResponse(code = 401,
                    message = "UNAUTHORIZED_USER"),
            @ApiResponse(code = 404,
                    message = "ANNIVERSARY_NOT_FOUND"),
            @ApiResponse(code = 500,
                    message = "SERVER_ERROR")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{anniversaryNo}")
    public ResponseEntity<DefaultResponseDto<Object>> readAnniversary(
            @PathVariable Long anniversaryNo){

        anniversaryService.isExistingAnniversaryNo(anniversaryNo);
        Anniversary anniversary = anniversaryService.findAnniversaryByAnniversaryNo(anniversaryNo);
        AnniversaryResponseDto dto = anniversaryService.returnDto(anniversary);

        return ResponseEntity.status(200)
                .body(resWithData("ANNIVERSARY_FOUND", "기념일 조회 완료", dto));
    }

    @ApiOperation("기념일 수정")
    @ApiResponses(value={
            @ApiResponse(code = 200,
                    message = "ANNIVERSARY_UPDATED",
                    response = AnniversaryResponseDto.class),
            @ApiResponse(code = 401,
                    message = "UNAUTHORIZED_USER"),
            @ApiResponse(code = 404,
                    message = "ANNIVERSARY_NOT_FOUND"),
            @ApiResponse(code = 500,
                    message = "SERVER_ERROR")
    })
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{anniversaryNo}")
    public ResponseEntity<DefaultResponseDto<Object>> updateAnniversary(
            @RequestBody AnniversaryUpdateRequestDto anniversaryUpdateRequestDto,
            @PathVariable Long anniversaryNo){

        anniversaryService.isExistingAnniversaryNo(anniversaryNo);

        Anniversary anniversary = anniversaryService.findAnniversaryByAnniversaryNo(anniversaryNo);
        anniversaryService.updateAnniversary(anniversaryUpdateRequestDto, anniversary);
        AnniversaryResponseDto dto = anniversaryService.returnDto(anniversary);

        return ResponseEntity.status(200)
                .body(resWithData("ANNIVERSARY_UPDATED", "기념일 수정 완료", dto));
    }

    @ApiOperation("기념일 핀 여부 수정")
    @ApiResponses(value={
            @ApiResponse(code = 200,
                    message = "ANNIVERSARY_PIN_UPDATED",
                    response = AnniversaryResponseDto.class),
            @ApiResponse(code = 401,
                    message = "UNAUTHORIZED_USER"),
            @ApiResponse(code = 404,
                    message = "ANNIVERSARY_NOT_FOUND"),
            @ApiResponse(code = 500,
                    message = "SERVER_ERROR")
    })
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/pin/{anniversaryNo}")
    public ResponseEntity<DefaultResponseDto<Object>> updateIsPinned(
            @PathVariable Long anniversaryNo){

        anniversaryService.isExistingAnniversaryNo(anniversaryNo);

        Anniversary anniversary = anniversaryService.findAnniversaryByAnniversaryNo(anniversaryNo);
        anniversaryService.updateIsPinned(anniversary);
        AnniversaryResponseDto dto = anniversaryService.returnDto(anniversary);

        return ResponseEntity.status(200)
                .body(resWithData("ANNIVERSARY_PIN_UPDATED", "기념일 핀 수정 완료", dto));
    }

    @ApiOperation("기념일 삭제")
    @ApiResponses(value={
            @ApiResponse(code = 200,
                    message = "ANNIVERSARY_DELETED",
                    response = AnniversaryResponseDto.class),
            @ApiResponse(code = 401,
                    message = "UNAUTHORIZED_USER"),
            @ApiResponse(code = 404,
                    message = "ANNIVERSARY_NOT_FOUND"),
            @ApiResponse(code = 500,
                    message = "SERVER_ERROR")
    })
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{anniversaryNo}")
    public ResponseEntity<DefaultResponseDto<Object>> deleteAnniversary(
            @PathVariable Long anniversaryNo){

        anniversaryService.isExistingAnniversaryNo(anniversaryNo);

        Anniversary anniversary = anniversaryService.findAnniversaryByAnniversaryNo(anniversaryNo);

        anniversaryService.deleteAnniversary(anniversaryNo);

        return ResponseEntity.status(200)
                .body(resWithoutData("ANNIVERSARY_DELETED", "기념일_삭제_완료"));
    }

    @ApiOperation("전체 기념일 조회")
    @ApiResponses(value={
            @ApiResponse(code = 200,
                    message = "ANNIVERSARIES_FOUND",
                    response = AnniversaryResponseDto.class),
            @ApiResponse(code = 401,
                    message = "UNAUTHORIZED_USER"),
            @ApiResponse(code = 500,
                    message = "SERVER_ERROR")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/admin")
    public ResponseEntity<DefaultResponseDto<Object>> readAll(){

        List<AnniversaryResponseDto> dto = anniversaryService.readAll();

        return ResponseEntity.status(200)
                .body(resWithData("ANIVERSARIES_FOUND", "전체 기념일 조회 완료", dto));
    }
}
