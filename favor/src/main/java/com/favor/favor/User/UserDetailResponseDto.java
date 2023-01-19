package com.favor.favor.User;

import com.favor.favor.Reminder.ReminderListResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class UserDetailResponseDto {
    private final Long userNo;
    private String name;
    private String userid;
    private String role;

    private List<ReminderListResponseDto> list;
    @Builder
    public UserDetailResponseDto(User user, List<ReminderListResponseDto> list){
        this.userNo = user.getUserNo();
        this.name = user.getName();
        this.userid = user.getUserId();
        this.role = user.getRole();
        this.list = list;
    }
}
