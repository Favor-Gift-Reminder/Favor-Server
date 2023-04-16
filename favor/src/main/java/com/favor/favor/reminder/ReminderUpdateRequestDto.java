package com.favor.favor.reminder;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReminderUpdateRequestDto {
    @ApiModelProperty(position = 1, required = true, dataType = "String", value = "제목", example = "제목")
    private String title;

    @ApiModelProperty(position = 2, required = true, dataType = "LocalDate", value = "날짜", example = "1996-02-29")
    private LocalDate reminderDate;

    @ApiModelProperty(position = 3, required = true, dataType = "Boolean", value = "알람설정여부", example = "페이버")
    private Boolean isAlarmSet;

    @ApiModelProperty(position = 4, required = false, dataType = "LocalDateTime", value = "1996-02-29 00:00")
    private LocalDateTime alarmTime;

    @ApiModelProperty(position = 5, required = false, dataType = "String", value = "메모", example = "페이버")
    private String reminderMemo;
}
