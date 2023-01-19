package com.favor.favor.Reminder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class ReminderResponseDto {
    private final Long reminderNo;
    private String title;
    private LocalDate eventDate;
    private String memo;
    private Boolean isAlarmSet;
    private LocalDateTime alarmTime;

    private Long userNo;

    @Builder
    public ReminderResponseDto(Reminder reminder){
        this.reminderNo = reminder.getReminderNo();
        this.title = reminder.getTitle();
        this.eventDate = reminder.getEventDate();
        this.memo = reminder.getMemo();
        this.isAlarmSet = reminder.getIsAlarmSet();
        this.alarmTime = reminder.getAlarmTime();
        this.userNo = reminder.getUser().getUserNo();
    }
}
