package com.favor.favor.anniversary;

import com.favor.favor.common.DefaultResponseDto;
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

@Api(tags = "Anniversary")
@RestController
@RequestMapping("/anniversaries")
@RequiredArgsConstructor
@Log4j2
public class AnniversaryController {

    private final AnniversaryService anniversaryService;

    @ApiOperation("기념일 생성")
    @ApiResponses(value={
            @ApiResponse(code = 201,
                    message = "REMINDER_CREATED",
                    response = AnniversaryResponseDto.class),
            @ApiResponse(code = 400,
                    message = "FILED_REQUIRED / *_CHARACTER_INVALID / *_LENGTH_INVALID"),
            @ApiResponse(code = 404,
                    message = "USER_NOT_FOUND / FREIND_NOT_FOUND"),
            @ApiResponse(code = 500,
                    message = "SERVER_ERROR")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{userNo}")
    public ResponseEntity<DefaultResponseDto<Object>> createAnniversary(
            @RequestBody AnniversaryRequestDto anniversaryRequestDto,
            @PathVariable Long userNo){

        anniversaryService.isExistingUserNo(userNo);

        Anniversary anniversary = anniversaryService.createAnniversary(anniversaryRequestDto, userNo);
        AnniversaryResponseDto dto = anniversaryService.returnDto(anniversary);

        return ResponseEntity.status(201)
                .body(DefaultResponseDto.builder()
                        .responseCode("ANNIVERSARY_CREATED")
                        .responseMessage("기념일 생성 완료")
                        .data(dto)
                        .build());
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
    @Transactional
    @GetMapping("/{anniversaryNo}")
    public ResponseEntity<DefaultResponseDto<Object>> readAnniversary(
            @PathVariable Long anniversaryNo){

        anniversaryService.isExistingAnniversaryNo(anniversaryNo);
        Anniversary anniversary = anniversaryService.findAnniversaryByAnniversaryNo(anniversaryNo);
        AnniversaryResponseDto dto = anniversaryService.returnDto(anniversary);

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("ANNIVERSARY_FOUND")
                        .responseMessage("기념일 조회 완료")
                        .data(dto)
                        .build());
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
    @Transactional
    @PatchMapping("/{anniversaryNo}")
    public ResponseEntity<DefaultResponseDto<Object>> updateAnniversary(
            @RequestBody AnniversaryUpdateRequestDto anniversaryUpdateRequestDto,
            @PathVariable Long anniversaryNo){

        anniversaryService.isExistingAnniversaryNo(anniversaryNo);

        Anniversary anniversary = anniversaryService.findAnniversaryByAnniversaryNo(anniversaryNo);
        anniversaryService.updateAnniversary(anniversaryUpdateRequestDto, anniversary);
        AnniversaryResponseDto dto = anniversaryService.returnDto(anniversary);

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseMessage("ANNIVERSARY_UPDATED")
                        .responseCode("기념일 수정 완료")
                        .data(dto)
                        .build());
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
    @Transactional
    @PatchMapping("/pin/{anniversaryNo}")
    public ResponseEntity<DefaultResponseDto<Object>> updateIsPinned(
            @PathVariable Long anniversaryNo){

        anniversaryService.isExistingAnniversaryNo(anniversaryNo);

        Anniversary anniversary = anniversaryService.findAnniversaryByAnniversaryNo(anniversaryNo);
        anniversaryService.updateIsPinned(anniversary);
        AnniversaryResponseDto dto = anniversaryService.returnDto(anniversary);

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseMessage("ANNIVERSARY_PIN_UPDATED")
                        .responseCode("기념일 핀 여부 수정 완료")
                        .data(dto)
                        .build());
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
    @Transactional
    @DeleteMapping("/{anniversaryNo}")
    public ResponseEntity<DefaultResponseDto<Object>> deleteAnniversary(
            @PathVariable Long anniversaryNo){

        anniversaryService.isExistingAnniversaryNo(anniversaryNo);

        Anniversary anniversary = anniversaryService.findAnniversaryByAnniversaryNo(anniversaryNo);
        AnniversaryResponseDto dto = anniversaryService.returnDto(anniversary);

        anniversaryService.deleteAnniversary(anniversaryNo);

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("ANNIVERSARY_DELETED")
                        .responseMessage("기념일 삭제 완료")
                        .data(dto)
                        .build());
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
    @Transactional
    @GetMapping
    public ResponseEntity<DefaultResponseDto<Object>> readAll(){

        List<AnniversaryResponseDto> dto = anniversaryService.readAll();

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("ANNIVERSARIES_FOUND")
                        .responseMessage("전체 기념일 조회 완료")
                        .data(dto)
                        .build());
    }
}
