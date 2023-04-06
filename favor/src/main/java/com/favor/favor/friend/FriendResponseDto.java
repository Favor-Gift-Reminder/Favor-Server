package com.favor.favor.friend;

import com.favor.favor.common.enums.Favor;
import com.favor.favor.gift.GiftResponseDto;
import com.favor.favor.reminder.ReminderResponseDto;
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

    private Long friendNo;
    private String friendName;
    private String friendMemo;


    private List<ReminderResponseDto> reminderList;
    private List<GiftResponseDto> giftList;
    private List<Favor> favorList;

    private Long friendUserNo;


    private Long userNo;

    @Builder
    public FriendResponseDto(Friend friend){
        this.isUser = friend.getIsUser();
        this.friendNo = friend.getFriendNo();
        this.friendName = friend.getFriendName();
        this.friendMemo = friend.getFriendMemo();
        this.friendUserNo = friend.getFriendUserNo();
        this.userNo = friend.getUser().getUserNo();
    }

//    @Builder
//    public FriendResponseDto(Friend friend, List<ReminderResponseDto> reminderList, List<Long> giftNoList, List<Favor> favorList){
//        this.isUser = friend.getIsUser();
//        this.friendNo = friend.getFriendNo();
//        this.friendName = friend.getFriendName();
//        this.friendMemo = friend.getFriendMemo();
//        this.reminderList = reminderList;
//        this.giftNoList = giftNoList;
//        this.favorList = favorList;
//        this.friendUserNo = friend.getFriendUserNo();
//        this.userNo = friend.getUser().getUserNo();
//    }

    @Builder
    public FriendResponseDto(Friend friend, List<ReminderResponseDto> reminderList, List<GiftResponseDto> giftList, List<Favor> favorList){
        this.isUser = friend.getIsUser();
        this.friendNo = friend.getFriendNo();
        this.friendName = friend.getFriendName();
        this.friendMemo = friend.getFriendMemo();
        this.reminderList = reminderList;
        this.giftList = giftList;
        this.favorList = favorList;
        this.friendUserNo = friend.getFriendUserNo();
        this.userNo = friend.getUser().getUserNo();
    }
}
