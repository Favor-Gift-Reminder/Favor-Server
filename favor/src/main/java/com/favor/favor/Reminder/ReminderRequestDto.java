package com.favor.favor.Reminder;

import com.favor.favor.Friend.Friend;
import com.favor.favor.User.User;
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
    @ApiModelProperty(position = 1, required = true, dataType = "String", value = "제목", example = "제목")
    private String title;

    @ApiModelProperty(position = 2, required = true, dataType = "LocalDate", value = "날짜", example = "1996-02-29")
    private LocalDate reminderDate;

    @ApiModelProperty(position = 3, required = true, dataType = "Boolean", value = "알람세팅여부", example = "False")
    private Boolean isAlarmSet;

    @ApiModelProperty(position = 4, required = true, dataType = "LocalDateTime", value = "알람세팅시간", example = "")
    private LocalDateTime alarmTime;

    @ApiModelProperty(position = 5, required = true, dataType = "String", value = "메모", example = "메모")
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
