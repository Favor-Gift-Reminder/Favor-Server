package com.favor.favor.reminder;

import com.favor.favor.friend.Friend;
import com.favor.favor.user.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReminderRequestDto {
    @ApiModelProperty(position = 1, required = true, value = "제목", example = "제목")
    private String reminderTitle;

    @ApiModelProperty(position = 2, required = true, value = "날짜", example = "1996-02-29")
    private String reminderDate;

    @ApiModelProperty(position = 3, required = true, value = "알람세팅여부", example = "False")
    private Boolean isAlarmSet;

    @ApiModelProperty(position = 4, required = true, value = "1996-02-29 00:00")
    private String alarmTime;

    @ApiModelProperty(position = 5, required = true, value = "메모", example = "메모")
    private String reminderMemo;

    @Transactional
    public Reminder toEntity(User user, Friend friend, LocalDate localDate, LocalDateTime localDateTime){
        return Reminder.builder()
                .reminderTitle(reminderTitle)
                .reminderDate(localDate)
                .isAlarmSet(isAlarmSet)
                .alarmTime(localDateTime)
                .reminderMemo(reminderMemo)
                .user(user)
                .friend(friend)
                .build();
    }


}
