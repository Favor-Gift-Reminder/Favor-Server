package com.favor.favor.User;

import com.favor.favor.Common.Favor;
import com.favor.favor.Common.Role;
import com.favor.favor.Friend.FriendResponseDto;
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
    private Role role;
    private List<ReminderResponseDto> reminderList;
    private List<GiftResponseDto> giftlist;
    private List<FriendResponseDto> friendList;
    private List<Favor> favorList;

    @Builder
    public UserDetailResponseDto(User user){
        this.userNo = user.getUserNo();
        this.name = user.getName();
        this.userid = user.getUserId();
        this.role = user.getRole();
    }

    @Builder
    public UserDetailResponseDto(User user, List<ReminderResponseDto> reminderList, List<GiftResponseDto> giftlist, List<FriendResponseDto> friendList, List<Favor> favor_List){
        this.userNo = user.getUserNo();
        this.name = user.getName();
        this.userid = user.getUserId();
        this.role = user.getRole();
        this.reminderList = reminderList;
        this.giftlist = giftlist;
        this.friendList = friendList;
        this.favorList = favor_List;
    }
}
