package com.favor.favor.Reminder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class ReminderDetailResponseDto {
    private final Long reminderNo;
    private String title;
    private LocalDate reminderDate;
    private String memo;
    private Boolean isAlarmSet;
    private LocalDateTime alarmTime;

    private Long userNo;

    @Builder
    public ReminderDetailResponseDto(Reminder reminder){
        this.reminderNo = reminder.getReminderNo();
        this.title = reminder.getTitle();
        this.reminderDate = reminder.getReminderDate();
        this.memo = reminder.getReminderMemo();
        this.isAlarmSet = reminder.getIsAlarmSet();
        this.alarmTime = reminder.getAlarmTime();
        this.userNo = reminder.getUser().getUserNo();
    }
}
