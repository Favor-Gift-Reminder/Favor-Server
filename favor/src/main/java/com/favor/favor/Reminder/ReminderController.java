package com.favor.favor.Reminder;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Reminder")
@RestController
@RequestMapping("/reminders")
@RequiredArgsConstructor
@Log4j2
public class ReminderController {
    private final ReminderService reminderService;

    @ApiOperation("리마인더 생성")
    @PostMapping
    public Long createReminder(@RequestBody ReminderRequestDto reminderRequestDto, Long userNo, Long friendNo){
        return reminderService.createReminder(reminderRequestDto, userNo, friendNo);
    }

    @ApiOperation("단일리마인더 조회")
    @GetMapping("/{reminderNo}")
    public ReminderDetailResponseDto readReminder(@PathVariable Long reminderNo){
        return reminderService.readReminder(reminderNo);
    }

    @ApiOperation("리마인더 수정")
    @PatchMapping("/{reminderNo}")
    public Long updateReminder(@RequestBody ReminderUpdateRequestDto dto, @PathVariable Long reminderNo, Long friendNo){
        return reminderService.updateReminder(reminderNo, dto, friendNo);
    }

    @ApiOperation("리마인더 삭제")
    @DeleteMapping("/{reminderNo}")
    public Long deleteReminder(@PathVariable Long reminderNo){
        reminderService.deleteReminder(reminderNo);
        return reminderNo;
    }

    @ApiOperation("전체 리마인더 조회")
    @GetMapping
    public List<ReminderResponseDto> readAll(){
        return reminderService.readAll();
    }
}
