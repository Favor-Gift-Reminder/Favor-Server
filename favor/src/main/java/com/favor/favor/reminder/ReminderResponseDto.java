package com.favor.favor.reminder;

import com.favor.favor.friend.FriendResponseDto;
import com.favor.favor.friend.FriendSimpleDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@AllArgsConstructor
@Builder
public class ReminderResponseDto {
    private final Long reminderNo;
    private String reminderTitle;
    private LocalDate reminderDate;

    private String reminderMemo;
    private Boolean isAlarmSet;
    private LocalDateTime alarmTime;

    private Long userNo;
    private FriendSimpleDto friendSimpleDto;

    @Builder
    public ReminderResponseDto(Reminder reminder, FriendSimpleDto dto){
        this.reminderNo = reminder.getReminderNo();
        this.reminderTitle = reminder.getReminderTitle();
        this.reminderDate = reminder.getReminderDate();
        this.reminderMemo = reminder.getReminderMemo();
        this.isAlarmSet = reminder.getIsAlarmSet();
        this.alarmTime = reminder.getAlarmTime();
        this.userNo = reminder.getUser().getUserNo();
        this.friendSimpleDto = dto;
    }
}
