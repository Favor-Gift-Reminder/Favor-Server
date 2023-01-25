package com.favor.favor.Friend.NoAccount;

import com.favor.favor.Friend.Friend;
import com.favor.favor.Gift.GiftResponseDto;
import com.favor.favor.Reminder.ReminderResponseDto;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FriendResponseDto {
    //friendName, friendMemo, giftList, reminderList
    private String friendName;
    private String friendMemo;
    private List<GiftResponseDto> giftList;
    private List<ReminderResponseDto> reminderList;

    @Builder
    public FriendResponseDto(Friend friend, List<GiftResponseDto> giftList, List<ReminderResponseDto> reminderList){
        this.friendName = friend.getFriendName();
        this.friendMemo = friend.getFriendMemo();
        this.giftList = giftList;
        this.reminderList = reminderList;
    }
}
