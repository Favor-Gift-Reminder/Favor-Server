package com.favor.favor.Friend.Account;

import com.favor.favor.Friend.Friend;
import com.favor.favor.Gift.GiftResponseDto;
import com.favor.favor.Reminder.ReminderResponseDto;
import com.favor.favor.User.User;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserFriendResponseDto {
    //friendName, friendMemo, giftList, reminderList

    private Long userNo;
    private String friendName;
    private String friendMemo;
    private List<GiftResponseDto> giftList;
    private List<ReminderResponseDto> reminderList;

    @Builder
    public UserFriendResponseDto(User user, Friend userFriend, List<GiftResponseDto> giftList, List<ReminderResponseDto> reminderList){
        this.userNo = user.getUserNo();
        this.friendName = user.getName();
        this.friendMemo = userFriend.getFriendMemo();
        this.giftList = giftList;
        this.reminderList = reminderList;
    }
}
