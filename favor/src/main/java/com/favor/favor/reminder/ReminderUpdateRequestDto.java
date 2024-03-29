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

    @ApiModelProperty(position = 4, required = false, dataType = "String", value = "알람 시간", example = "1996-02-29 00:00")
    private String alarmTime;

    @ApiModelProperty(position = 5, required = false, dataType = "String", value = "메모", example = "페이버")
    private String reminderMemo;

    @ApiModelProperty(position = 6, required = false, dataType = "Long", value = "친구 식별자", example = "1")
    private Long friendNo;

}
