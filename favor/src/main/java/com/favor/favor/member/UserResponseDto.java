package com.favor.favor.member;

import com.favor.favor.anniversary.AnniversaryResponseDto;
import com.favor.favor.common.enums.Favor;
import com.favor.favor.common.enums.Role;
import com.favor.favor.friend.FriendResponseDto;
import com.favor.favor.gift.GiftResponseDto;
import com.favor.favor.reminder.ReminderResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class  UserResponseDto {
    private final Long userNo;
    private String email;
    private String name;
    private String userid;
    private Role role;
    private List<ReminderResponseDto> reminderList;
    private List<GiftResponseDto> giftList;
    private List<FriendResponseDto> friendList;
    private List<AnniversaryResponseDto> anniversaryList;
    private List<Favor> favorList;

    @Builder
    public UserResponseDto(User user,
                           List<ReminderResponseDto> reminderList,
                           List<GiftResponseDto> giftList,
                           List<FriendResponseDto> friendList,
                           List<Favor> favorList,
                           List<AnniversaryResponseDto> anniversaryList){
        this.userNo = user.getUserNo();
        this.email = user.getEmail();
        this.name = user.getName();
        this.userid = user.getUserId();
        this.role = user.getRole();
        this.reminderList = reminderList;
        this.giftList = giftList;
        this.friendList = friendList;
        this.favorList = favorList;
        this.anniversaryList = anniversaryList;
    }
}
