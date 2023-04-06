package com.favor.favor.reminder;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReminderUpdateRequestDto {
    //title, eventDate, isAlarmSet, alarmTime, memo 수정 가능
    @ApiModelProperty(position = 1, required = true, dataType = "String", value = "제목", example = "제목")
    private String title;

    @ApiModelProperty(position = 2, required = true, dataType = "LocalDate", value = "날짜", example = "1996-02-29")
    private Date reminderDate;

    @ApiModelProperty(position = 3, required = true, dataType = "Boolean", value = "알람설정여부", example = "페이버")
    private Boolean isAlarmSet;

    @ApiModelProperty(position = 4, required = false, dataType = "LocalDateTime", value = "알람세팅시간")
    private Date alarmTime;

    @ApiModelProperty(position = 5, required = false, dataType = "String", value = "메모", example = "페이버")
    private String reminderMemo;
}
