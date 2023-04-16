package com.favor.favor.friend;

import com.favor.favor.anniversary.AnniversaryResponseDto;
import com.favor.favor.common.enums.Favor;
import com.favor.favor.gift.GiftResponseDto;
import com.favor.favor.reminder.ReminderResponseDto;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class FriendResponseDto {

    private Boolean isUser;

    private Long friendNo;
    private String friendName;
    private String friendMemo;


    private List<ReminderResponseDto> reminderList;
    private List<GiftResponseDto> giftList;
    private List<Favor> favorList;

    private Long friendUserNo;


    private List<AnniversaryResponseDto> anniversaryList;
    private Long userNo;

    @Builder
    public FriendResponseDto(Friend friend){
        this.isUser = friend.getIsUser();
        this.friendNo = friend.getFriendNo();
        this.friendName = friend.getFriendName();
        this.friendMemo = friend.getFriendMemo();
        this.reminderList = new ArrayList<>();
        this.giftList = new ArrayList<>();
        this.favorList = new ArrayList<>();
        this.anniversaryList = new ArrayList<>();
        this.friendUserNo = friend.getFriendUserNo();
        this.userNo = friend.getUser().getUserNo();
    }

    @Builder
    public FriendResponseDto(Friend friend,
                             List<ReminderResponseDto> reminderList,
                             List<GiftResponseDto> giftList,
                             List<Favor> favorList,
                             List<AnniversaryResponseDto> anniversaryList
    ){
        this.isUser = friend.getIsUser();
        this.friendNo = friend.getFriendNo();
        this.friendName = friend.getFriendName();
        this.friendMemo = friend.getFriendMemo();
        this.reminderList = reminderList;
        this.giftList = giftList;
        this.favorList = favorList;
        this.anniversaryList = anniversaryList;
        this.friendUserNo = friend.getFriendUserNo();
        this.userNo = friend.getUser().getUserNo();
    }
}
