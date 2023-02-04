package com.favor.favor.Friend;

import com.favor.favor.Gift.GiftResponseDto;
import com.favor.favor.Reminder.ReminderResponseDto;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FriendResponseDto {
    //Service 단에서 isUser 로 분기점
    //UserFriend 의 경우 : friendName, friendMemo, giftList, reminderList, [favorList], [userNo]
    //Friend 의 경우 : friendName, friendMemo, giftList, reminderList

    private Boolean isUser;
    private String friendName;
    private String friendMemo;


    private List<GiftResponseDto> giftList;
    private List<ReminderResponseDto> reminderList;


    private Long userFriendNo;


    private Long userNo;

    @Builder
    public FriendResponseDto(Friend friend, List<GiftResponseDto> giftList, List<ReminderResponseDto> reminderList){
        this.isUser = friend.getIsUser();
        this.friendName = friend.getFriendName();
        this.friendMemo = friend.getFriendMemo();
        this.giftList = giftList;
        this.reminderList = reminderList;
        this.userFriendNo = friend.getUserFriendNo();
    }
}
