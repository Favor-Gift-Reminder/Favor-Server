package com.favor.favor.reminder;

import com.favor.favor.friend.FriendSimpleDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@Builder
public class ReminderSimpleDto {
    private final Long reminderNo;
    private String reminderTitle;
    private LocalDate reminderDate;
    private boolean isAlarmSet;
    private Long userNo;

    @Builder
    public ReminderSimpleDto(Reminder reminder){
        this.reminderNo = reminder.getReminderNo();
        this.reminderTitle = reminder.getReminderTitle();
        this.reminderDate = reminder.getReminderDate();
        this.isAlarmSet = reminder.getIsAlarmSet();
        this.userNo = reminder.getUser().getUserNo();
    }
}
