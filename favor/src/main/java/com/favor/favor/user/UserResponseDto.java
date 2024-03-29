package com.favor.favor.user;

import com.favor.favor.anniversary.AnniversaryResponseDto;
import com.favor.favor.common.enums.Favor;
import com.favor.favor.common.enums.Role;
import com.favor.favor.friend.FriendSimpleDto;
import com.favor.favor.photo.UserPhoto;
import com.favor.favor.reminder.ReminderSimpleDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class  UserResponseDto {
    @ApiModelProperty(value = "1")
    private final Long userNo;

    @ApiModelProperty(value = "favor@gmail.com")
    private String email;

    @ApiModelProperty(value = "페이버")
    private String name;

    @ApiModelProperty(value = "favor")
    private String userId;

    @ApiModelProperty(value = "USER")
    private Role role;

    @ApiModelProperty(value = "1")
    private int givenGift;

    @ApiModelProperty(value = "1")
    private int receivedGift;

    @ApiModelProperty(value = "2")
    private int totalGift;

    @ApiModelProperty(value = "")
    private List<ReminderSimpleDto> reminderList;

    @ApiModelProperty(value = "")
    private List<FriendSimpleDto> friendList;

    @ApiModelProperty(value = "")
    private List<AnniversaryResponseDto> anniversaryList;

    @ApiModelProperty(value = "")
    private List<Favor> favorList;

    @ApiModelProperty(value = "")
    private UserPhoto userProfileUserPhoto;

    @ApiModelProperty(value = "")
    private UserPhoto userBackgroundUserPhoto;

    @Builder
    public UserResponseDto(User user,
                           List<ReminderSimpleDto> reminderList,
                           List<FriendSimpleDto> friendList,
                           List<Favor> favorList,
                           List<AnniversaryResponseDto> anniversaryList,
                           HashMap<String, Integer> giftInfo){
        this.userNo = user.getUserNo();
        this.email = user.getEmail();
        this.name = user.getName();
        this.userId = user.getUserId();
        this.role = user.getRole();
        this.givenGift = giftInfo.get("given");
        this.receivedGift = giftInfo.get("received");
        this.totalGift = giftInfo.get("total");
        this.reminderList = reminderList;
        this.friendList = friendList;
        this.favorList = favorList;
        this.anniversaryList = anniversaryList;
        this.userProfileUserPhoto = user.getUserProfilePhoto();
        this.userBackgroundUserPhoto = user.getUserBackgroundPhoto();
    }
}
