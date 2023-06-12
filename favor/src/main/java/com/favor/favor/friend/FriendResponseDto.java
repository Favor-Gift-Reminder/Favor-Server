package com.favor.favor.friend;

import com.favor.favor.anniversary.AnniversaryResponseDto;
import com.favor.favor.common.enums.Favor;
import com.favor.favor.gift.GiftResponseDto;
import com.favor.favor.reminder.ReminderResponseDto;
import lombok.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
@AllArgsConstructor
public class FriendResponseDto {

    private Boolean isUser;

    private Long friendNo;
    private String friendName;
    private String friendMemo;

    private List<ReminderResponseDto> reminderList;
    private List<Favor> favorList;
    private List<Long> anniversaryNoList;

    private int givenGift;
    private int receivedGift;
    private int totalGift;

    private Long friendUserNo;
    private Long userNo;

    @Builder
    public FriendResponseDto(Friend friend){
        this.isUser = friend.getIsUser();
        this.friendNo = friend.getFriendNo();
        this.friendName = friend.getFriendName();
        this.friendMemo = friend.getFriendMemo();
        this.reminderList = new ArrayList<>();
        this.favorList = new ArrayList<>();
        this.anniversaryNoList = new ArrayList<>();
        this.friendUserNo = friend.getFriendUserNo();
        this.userNo = friend.getUser().getUserNo();
    }

    @Builder
    public FriendResponseDto(Friend friend,
                             List<ReminderResponseDto> reminderList,
                             List<Favor> favorList,
                             List<Long> anniversaryNoList,
                             HashMap<String, Integer> giftInfo
    ){
        this.isUser = friend.getIsUser();
        this.friendNo = friend.getFriendNo();
        this.friendName = friend.getFriendName();
        this.friendMemo = friend.getFriendMemo();
        this.reminderList = reminderList;
        this.favorList = favorList;
        this.anniversaryNoList = anniversaryNoList;
        this.givenGift = giftInfo.get("given");
        this.receivedGift = giftInfo.get("received");
        this.totalGift = giftInfo.get("total");
        this.friendUserNo = friend.getFriendUserNo();
        this.userNo = friend.getUser().getUserNo();
    }
}
