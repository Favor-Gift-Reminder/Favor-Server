package com.favor.favor.reminder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
@Builder
public class ReminderResponseDto {
    private final Long reminderNo;
    private String title;
    private Date reminderDate;
    private String memo;
    private Boolean isAlarmSet;
    private Date alarmTime;

    private Long userNo;
    private Long friendNo;

    @Builder
    public ReminderResponseDto(Reminder reminder){
        this.reminderNo = reminder.getReminderNo();
        this.title = reminder.getTitle();
        this.reminderDate = reminder.getReminderDate();
        this.memo = reminder.getReminderMemo();
        this.isAlarmSet = reminder.getIsAlarmSet();
        this.alarmTime = reminder.getAlarmTime();
        this.userNo = reminder.getUser().getUserNo();
        this.friendNo = reminder.getFriend().getFriendNo();
    }
}
