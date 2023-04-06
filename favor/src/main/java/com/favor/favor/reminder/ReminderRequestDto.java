package com.favor.favor.reminder;

import com.favor.favor.friend.Friend;
import com.favor.favor.user.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.transaction.Transactional;
import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReminderRequestDto {
    @ApiModelProperty(position = 1, required = true, value = "제목", example = "제목")
    private String title;

    @ApiModelProperty(position = 2, required = true, value = "날짜", example = "1996-02-29")
    private Date reminderDate;

    @ApiModelProperty(position = 3, required = true, value = "알람세팅여부", example = "False")
    private Boolean isAlarmSet;

    @ApiModelProperty(position = 4, required = true, value = "알람세팅시간")
    private Date alarmTime;

    @ApiModelProperty(position = 5, required = true, value = "메모", example = "메모")
    private String reminderMemo;

    @Transactional
    public Reminder toEntity(User user, Friend friend){
        return Reminder.builder()
                .title(title)
                .reminderDate(reminderDate)
                .isAlarmSet(isAlarmSet)
                .alarmTime(alarmTime)
                .reminderMemo(reminderMemo)
                .user(user)
                .friend(friend)
                .build();
    }


}
