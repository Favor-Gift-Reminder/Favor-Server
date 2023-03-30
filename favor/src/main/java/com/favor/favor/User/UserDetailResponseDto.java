package com.favor.favor.User;

import com.favor.favor.Enum.Favor;
import com.favor.favor.Enum.Role;
import com.favor.favor.Friend.FriendDetailResponseDto;
import com.favor.favor.Gift.GiftDetailResponseDto;
import com.favor.favor.Reminder.ReminderDetailResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class UserDetailResponseDto {
    private final Long userNo;
    private String email;
    private String name;
    private String userid;
    private Role role;
    private List<ReminderDetailResponseDto> reminderList;
    private List<GiftDetailResponseDto> giftlist;
    private List<FriendDetailResponseDto> friendList;
    private List<Favor> favorList;

    @Builder
    public UserDetailResponseDto(User user, List<ReminderDetailResponseDto> reminderList, List<GiftDetailResponseDto> giftlist, List<FriendDetailResponseDto> friendList, List<Favor> favor_List){
        this.userNo = user.getUserNo();
        this.email = user.getEmail();
        this.name = user.getName();
        this.userid = user.getUserId();
        this.role = user.getRole();
        this.reminderList = reminderList;
        this.giftlist = giftlist;
        this.friendList = friendList;
        this.favorList = favor_List;
    }
}
