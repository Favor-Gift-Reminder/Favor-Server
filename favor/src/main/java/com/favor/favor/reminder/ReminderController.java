package com.favor.favor.reminder;


import com.favor.favor.common.DefaultResponseDto;
import com.favor.favor.user.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@Api(tags = "Reminder")
@RestController
@RequestMapping("/reminders")
@RequiredArgsConstructor
@Log4j2
public class ReminderController {

    private final ReminderService reminderService;

    @ApiOperation("리마인더 생성")
    @ApiResponses(value={
            @ApiResponse(code = 201,
                    message = "REMINDER_CREATED",
                    response = ReminderResponseDto.class),
            @ApiResponse(code = 400,
                    message = "FIELD_REQUIRED / *_CHARACTER_INVALID / *_LENGTH_INVALID"),
            @ApiResponse(code = 404,
                    message = "USER_NOT_FOUND / FREIND_NOT_FOUND"),
            @ApiResponse(code = 500,
                    message = "SERVER_ERROR")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/new/{friendNo}")
    public ResponseEntity<DefaultResponseDto<Object>> createReminder(
            @RequestBody ReminderRequestDto reminderRequestDto,
            @AuthenticationPrincipal User loginUser,
            @PathVariable Long friendNo){

        Long userNo = loginUser.getUserNo();

        reminderService.isExistingUserNo(userNo);
        reminderService.isExistingFriendNo(friendNo);

        Reminder reminder = reminderService.createReminder(reminderRequestDto, userNo, friendNo);
        ReminderResponseDto dto = reminderService.returnDto(reminder);

        return ResponseEntity.status(201)
                .body(DefaultResponseDto.builder()
                        .responseCode("REMINDER_CREATED")
                        .responseMessage("리마인더 생성 완료")
                        .data(dto)
                        .build());
    }

    @ApiOperation("친구의 기념일을 리마인더로 추가")
    @ApiResponses(value={
            @ApiResponse(code = 201,
                    message = "REMINDER_ADDED",
                    response = ReminderResponseDto.class),
            @ApiResponse(code = 400,
                    message = "FILED_REQUIRED / *_CHARACTER_INVALID / *_LENGTH_INVALID"),
            @ApiResponse(code = 404,
                    message = "USER_NOT_FOUND / FREIND_NOT_FOUND"),
            @ApiResponse(code = 500,
                    message = "SERVER_ERROR")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{anniversaryNo}")
    public ResponseEntity<DefaultResponseDto<Object>> addReminder(
            @AuthenticationPrincipal User loginUser,
            @PathVariable Long anniversaryNo){

        Long userNo = loginUser.getUserNo();

        reminderService.isExistingUserNo(userNo);
        reminderService.isExistingAnniversaryNo(anniversaryNo);

        Reminder reminder = reminderService.addReminder(userNo, anniversaryNo);
        ReminderResponseDto dto = reminderService.returnDto(reminder);

        return ResponseEntity.status(201)
                .body(DefaultResponseDto.builder()
                        .responseCode("REMINDER_ADDED")
                        .responseMessage("리마인더 추가 완료")
                        .data(dto)
                        .build());
    }

    @ApiOperation("리마인더 조회")
    @ApiResponses(value={
            @ApiResponse(code = 200,
                    message = "REMINDER_FOUND",
                    response = ReminderResponseDto.class),
            @ApiResponse(code = 401,
                    message = "UNAUTHORIZED_USER"),
            @ApiResponse(code = 404,
                    message = "REMINDER_NOT_FOUND"),
            @ApiResponse(code = 500,
                    message = "SERVER_ERROR")
    })
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    @GetMapping("/{reminderNo}")
    public ResponseEntity<DefaultResponseDto<Object>> readReminder(
            @PathVariable Long reminderNo){

        reminderService.isExistingReminderNo(reminderNo);
        Reminder reminder = reminderService.findReminderByReminderNo(reminderNo);
        ReminderResponseDto dto = reminderService.returnDto(reminder);

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("REMINDER_FOUND")
                        .responseMessage("리마인더 조회 완료")
                        .data(dto)
                        .build());
    }

    @ApiOperation("리마인더 수정")
    @ApiResponses(value={
            @ApiResponse(code = 200,
                    message = "REMINDER_UPDATED",
                    response = ReminderResponseDto.class),
            @ApiResponse(code = 401,
                    message = "UNAUTHORIZED_USER"),
            @ApiResponse(code = 404,
                    message = "REMINDER_NOT_FOUND / FRIEND_NOT_FOUND"),
            @ApiResponse(code = 500,
                    message = "SERVER_ERROR")
    })
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    @PatchMapping("/{reminderNo}")
    public ResponseEntity<DefaultResponseDto<Object>> updateReminder(
            @RequestBody ReminderUpdateRequestDto reminderUpdateRequestDto,
            @PathVariable Long reminderNo, Long friendNo){

        reminderService.isExistingReminderNo(reminderNo);
        reminderService.isExistingFriendNo(friendNo);

        Reminder reminder = reminderService.findReminderByReminderNo(reminderNo);
        reminderService.updateReminder(reminderUpdateRequestDto, reminderNo);
        ReminderResponseDto dto = reminderService.returnDto(reminder);

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("REMINDER_UPDATED")
                        .responseMessage("리마인더 수정 완료")
                        .data(dto)
                        .build());
    }

    @ApiOperation("리마인더 삭제")
    @ApiResponses(value={
            @ApiResponse(code = 200,
                    message = "REMINDER_DELETED",
                    response = ReminderResponseDto.class),
            @ApiResponse(code = 401,
                    message = "UNAUTHORIZED_USER"),
            @ApiResponse(code = 404,
                    message = "REMINDER_NOT_FOUND"),
            @ApiResponse(code = 500,
                    message = "SERVER_ERROR")
    })
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    @DeleteMapping("/{reminderNo}")
    public ResponseEntity<DefaultResponseDto<Object>> deleteReminder(
            @PathVariable Long reminderNo){

        reminderService.isExistingReminderNo(reminderNo);

        Reminder reminder = reminderService.findReminderByReminderNo(reminderNo);
        ReminderResponseDto dto = reminderService.returnDto(reminder);

        reminderService.deleteReminder(reminderNo);

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("REMINDER_DELETED")
                        .responseMessage("리마인더 삭제 완료")
                        .data(dto)
                        .build());
    }

    @ApiOperation("전체 리마인더 조회")
    @ApiResponses(value={
            @ApiResponse(code = 200,
                    message = "REMIDNERS_FOUND",
                    response = ReminderResponseDto.class),
            @ApiResponse(code = 401,
                    message = "UNAUTHORIZED_USER"),
            @ApiResponse(code = 500,
                    message = "SERVER_ERROR")
    })
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    @GetMapping("/admin")
    public ResponseEntity<DefaultResponseDto<Object>> readAll(){

        List<ReminderResponseDto> dto = reminderService.readAll();

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.builder()
                        .responseCode("REMINDERS_FOUND")
                        .responseMessage("전체 리마인더 조회 완료")
                        .data(dto)
                        .build());
    }


}
