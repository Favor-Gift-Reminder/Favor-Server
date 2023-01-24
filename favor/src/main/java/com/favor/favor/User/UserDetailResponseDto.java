package com.favor.favor.User;

import com.favor.favor.Gift.GiftResponseDto;
import com.favor.favor.Reminder.ReminderResponseDto;
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

    private List<ReminderResponseDto> reminderList;
    private List<GiftResponseDto> giftlist;
    @Builder
    public UserDetailResponseDto(User user, List<ReminderResponseDto> reminderList, List<GiftResponseDto> giftlist){
        this.userNo = user.getUserNo();
        this.name = user.getName();
        this.userid = user.getUserId();
        this.role = user.getRole();
        this.reminderList = reminderList;
        this.giftlist = giftlist;

    }
}
