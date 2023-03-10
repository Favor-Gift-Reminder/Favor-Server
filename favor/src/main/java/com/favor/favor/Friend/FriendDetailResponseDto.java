package com.favor.favor.Friend;

import com.favor.favor.Common.Favor;
import com.favor.favor.Gift.GiftResponseDto;
import com.favor.favor.Reminder.ReminderResponseDto;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FriendDetailResponseDto {
    //Service 단에서 isUser 로 분기점
    //UserFriend 의 경우 : friendName, friendMemo, giftList, reminderList, [favorList], [userNo]
    //Friend 의 경우 : friendName, friendMemo, giftList, reminderList

    private Boolean isUser;

    private Long friendNo;
    private String friendName;
    private String friendMemo;


    private List<ReminderResponseDto> reminderList;
    private List<Favor> favorList;

    private Long userFriendNo;


    private Long userNo;

    @Builder
    public FriendDetailResponseDto(Friend friend){
        this.isUser = friend.getIsUser();
        this.friendNo = friend.getFriendNo();
        this.friendName = friend.getFriendName();
        this.friendMemo = friend.getFriendMemo();
        this.userFriendNo = friend.getUserFriendNo();
        this.userNo = friend.getUser().getUserNo();
    }

    @Builder
    public FriendDetailResponseDto(Friend friend, List<ReminderResponseDto> reminderList){
        this.isUser = friend.getIsUser();
        this.friendNo = friend.getFriendNo();
        this.friendName = friend.getFriendName();
        this.friendMemo = friend.getFriendMemo();
        this.reminderList = reminderList;
        this.favorList = favorList;
        this.userFriendNo = friend.getUserFriendNo();
        this.userNo = friend.getUser().getUserNo();
    }

    @Builder
    public FriendDetailResponseDto(Friend friend, List<ReminderResponseDto> reminderList, List<Favor> favorList){
        this.isUser = friend.getIsUser();
        this.friendNo = friend.getFriendNo();
        this.friendName = friend.getFriendName();
        this.friendMemo = friend.getFriendMemo();
        this.reminderList = reminderList;
        this.favorList = favorList;
        this.userFriendNo = friend.getUserFriendNo();
        this.userNo = friend.getUser().getUserNo();
    }
}
