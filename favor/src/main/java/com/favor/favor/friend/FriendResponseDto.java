package com.favor.favor.friend;

import com.favor.favor.anniversary.AnniversaryResponseDto;
import com.favor.favor.common.enums.Favor;
import com.favor.favor.photo.UserPhoto;
import com.favor.favor.reminder.ReminderResponseDto;
import com.favor.favor.reminder.ReminderSimpleDto;
import com.favor.favor.user.User;
import lombok.*;

import java.util.HashMap;
import java.util.List;

@Getter
@AllArgsConstructor
public class FriendResponseDto {

    private Long friendNo;
    private String friendName;
    private String friendMemo;

    private List<ReminderSimpleDto> reminderList;
    private List<Favor> favorList;
    private List<AnniversaryResponseDto> anniversaryList;

    private int givenGift;
    private int receivedGift;
    private int totalGift;

    private Long userNo;
    private String friendId;
    private UserPhoto photo;

    @Builder
    public FriendResponseDto(Friend friend,
                             User friendUser,
                             List<ReminderSimpleDto> reminderList,
                             List<Favor> favorList,
                             List<AnniversaryResponseDto> anniversaryList,
                             HashMap<String, Integer> giftInfo,
                             String friendId
    ){
        this.friendNo = friend.getFriendNo();
        this.friendName = friendUser.getName();
        this.friendMemo = friend.getFriendMemo();
        this.reminderList = reminderList;
        this.favorList = favorList;
        this.anniversaryList = anniversaryList;
        this.givenGift = giftInfo.get("given");
        this.receivedGift = giftInfo.get("received");
        this.totalGift = giftInfo.get("total");
        this.userNo = friend.getFriendUserNo();
        this.friendId = friendId;
        this.photo = friendUser.getUserProfilePhoto();
    }
}
