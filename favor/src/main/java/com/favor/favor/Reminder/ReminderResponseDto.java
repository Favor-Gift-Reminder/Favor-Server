package com.favor.favor.Reminder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@Builder
public class ReminderResponseDto {
    private final Long reminderNo;
    private String title;
    private LocalDate eventDate;
    private Boolean isAlarmSet;
    private Long userNo;
    private Long friendNo;

    @Builder
    public ReminderResponseDto(Reminder reminder){
        this.reminderNo = reminder.getReminderNo();
        this.title = reminder.getTitle();
        this.eventDate = reminder.getReminderDate();
        this.isAlarmSet = reminder.getIsAlarmSet();
        this.userNo = reminder.getUser().getUserNo();
        this.friendNo = reminder.getFriend().getFriendNo();
    }
}
