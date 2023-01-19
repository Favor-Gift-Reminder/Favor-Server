package com.favor.favor.Reminder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
@Builder
public class ReminderListResponseDto {
    private final Long reminderNo;
    private String title;
    private LocalDate eventDate;
    private Boolean isAlarmSet;
    private Long userNo;

    @Builder
    public ReminderListResponseDto(Reminder reminder){
        this.reminderNo = reminder.getReminderNo();
        this.title = reminder.getTitle();
        this.eventDate = reminder.getEventDate();
        this.isAlarmSet = reminder.getIsAlarmSet();
        this.userNo = reminder.getUser().getUserNo();
    }
}
