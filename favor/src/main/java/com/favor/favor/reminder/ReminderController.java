package com.favor.favor.reminder;


import com.favor.favor.common.DefaultResponseDto;
import com.favor.favor.user.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.favor.favor.common.DefaultResponseDto.from;
import static com.favor.favor.common.DefaultResponseDto.from;

@Api(tags = "Reminder")
@RestController
@RequestMapping("/reminders")
@RequiredArgsConstructor
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
                    message = "USER_NOT_FOUND / FRIEND_NOT_FOUND"),
            @ApiResponse(code = 500,
                    message = "SERVER_ERROR")
    })
    @PostMapping("/new")
    public ResponseEntity<DefaultResponseDto<Object>> createReminder(
            @RequestBody ReminderRequestDto reminderRequestDto,
            @AuthenticationPrincipal User loginUser){

        Long userNo = loginUser.getUserNo();

        reminderService.isExistingUserNo(userNo);
        if(reminderRequestDto.getFriendNo() != null){
            reminderService.isExistingFriendNo(reminderRequestDto.getFriendNo());
        }
        Reminder reminder = null;
        if(reminderRequestDto.getFriendNo() != null){
            reminder = reminderService.createReminder(reminderRequestDto, userNo, reminderRequestDto.getFriendNo());
        }else{
            reminder = reminderService.createReminder(reminderRequestDto, userNo, null);
        }
        ReminderResponseDto dto = reminderService.returnDto(reminder);

        return ResponseEntity.status(201)
                .body(DefaultResponseDto.from("REMINDER_CREATED", "리마인더 생성 완료", dto));
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
                .body(DefaultResponseDto.from("REMINDER_ADDED", "리마인더 추가 완료", dto));
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
    @GetMapping("/{reminderNo}")
    public ResponseEntity<DefaultResponseDto<Object>> readReminder(
            @PathVariable Long reminderNo){

        reminderService.isExistingReminderNo(reminderNo);
        Reminder reminder = reminderService.findReminderByReminderNo(reminderNo);
        ReminderResponseDto dto = reminderService.returnDto(reminder);

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.from("REMINDER_FOUND", "리마인더 조회 완료", dto));
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
    @PatchMapping("/{reminderNo}")
    public ResponseEntity<DefaultResponseDto<Object>> updateReminder(
            @RequestBody ReminderUpdateRequestDto reminderUpdateRequestDto,
            @PathVariable Long reminderNo){

        reminderService.isExistingReminderNo(reminderNo);

        Reminder reminder = reminderService.findReminderByReminderNo(reminderNo);
        reminderService.updateReminder(reminderUpdateRequestDto, reminderNo);
        ReminderResponseDto dto = reminderService.returnDto(reminder);

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.from("REMINDER_UPDATED", "리마인더 수정 완료", dto));
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
    @DeleteMapping("/{reminderNo}")
    public ResponseEntity<DefaultResponseDto<Object>> deleteReminder(
            @PathVariable Long reminderNo){

        reminderService.isExistingReminderNo(reminderNo);

        Reminder reminder = reminderService.findReminderByReminderNo(reminderNo);
        ReminderResponseDto dto = reminderService.returnDto(reminder);

        reminderService.deleteReminder(reminderNo);

        return ResponseEntity.status(200)
                .body(from("REMINDER_DELETED", "리마인더 삭제 완료"));
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
    @GetMapping("/admin")
    public ResponseEntity<DefaultResponseDto<Object>> readAll(){

        List<ReminderSimpleDto> dto = reminderService.readAll();

        return ResponseEntity.status(200)
                .body(DefaultResponseDto.from("REMINDERS_FOUND", "전체 리마인더 조회 완료", dto));
    }


}
