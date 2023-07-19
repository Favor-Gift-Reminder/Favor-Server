package com.favor.favor.friend;

import com.favor.favor.anniversary.AnniversaryResponseDto;
import com.favor.favor.common.enums.Favor;
import com.favor.favor.reminder.ReminderResponseDto;
import com.favor.favor.user.User;
import lombok.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
@AllArgsConstructor
public class FriendResponseDto {

    private Long friendNo;
    private String friendName;
    private String friendMemo;

    private List<ReminderResponseDto> reminderList;
    private List<Favor> favorList;
    private List<AnniversaryResponseDto> anniversaryList;

    private int givenGift;
    private int receivedGift;
    private int totalGift;

    private Long userNo;

    @Builder
    public FriendResponseDto(Friend friend){
        this.friendNo = friend.getFriendNo();
        this.friendName = friend.getFriendName();
        this.friendMemo = friend.getFriendMemo();
        this.reminderList = new ArrayList<>();
        this.favorList = new ArrayList<>();
        this.anniversaryList = new ArrayList<>();
        this.givenGift = 0;
        this.receivedGift = 0;
        this.totalGift = 0;
        this.userNo = friend.getFriendUserNo();
    }

    @Builder
    public FriendResponseDto(Friend friend, User user){
        this.friendNo = friend.getFriendNo();
        this.friendName = user.getUsername();
        this.friendMemo = friend.getFriendMemo();
        this.reminderList = new ArrayList<>();
        this.favorList = new ArrayList<>();
        this.anniversaryList = new ArrayList<>();
        this.givenGift = 0;
        this.receivedGift = 0;
        this.totalGift = 0;
        this.userNo = user.getUserNo();
    }

    @Builder
    public FriendResponseDto(Friend friend,
                             List<ReminderResponseDto> reminderList,
                             List<Favor> favorList,
                             List<AnniversaryResponseDto> anniversaryList,
                             HashMap<String, Integer> giftInfo
    ){
        this.friendNo = friend.getFriendNo();
        this.friendName = friend.getFriendName();
        this.friendMemo = friend.getFriendMemo();
        this.reminderList = reminderList;
        this.favorList = favorList;
        this.anniversaryList = anniversaryList;
        this.givenGift = giftInfo.get("given");
        this.receivedGift = giftInfo.get("received");
        this.totalGift = giftInfo.get("total");
        this.userNo = friend.getFriendUserNo();
    }
}
