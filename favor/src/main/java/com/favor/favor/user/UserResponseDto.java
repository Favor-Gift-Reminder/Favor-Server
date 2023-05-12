package com.favor.favor.user;

import com.favor.favor.anniversary.AnniversaryResponseDto;
import com.favor.favor.common.enums.Favor;
import com.favor.favor.common.enums.Role;
import com.favor.favor.friend.FriendResponseDto;
import com.favor.favor.gift.GiftResponseDto;
import com.favor.favor.reminder.ReminderResponseDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

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
    private String userid;
    @ApiModelProperty(value = "USER")
    private Role role;
    @ApiModelProperty(value = "")
    private List<ReminderResponseDto> reminderList;
    @ApiModelProperty(value = "")
    private List<GiftResponseDto> giftList;
    @ApiModelProperty(value = "")
    private List<FriendResponseDto> friendList;
    @ApiModelProperty(value = "")
    private List<AnniversaryResponseDto> anniversaryList;
    @ApiModelProperty(value = "")
    private List<Favor> favorList;

    @Builder
    public UserResponseDto(User user,
                           List<ReminderResponseDto> reminderList,
                           List<GiftResponseDto> giftList,
                           List<FriendResponseDto> friendList,
                           List<Favor> favorList,
                           List<AnniversaryResponseDto> anniversaryList){
        this.userNo = user.getUserNo();
        this.email = user.getEmail();
        this.name = user.getName();
        this.userid = user.getUserId();
        this.role = user.getRole();
        this.reminderList = reminderList;
        this.giftList = giftList;
        this.friendList = friendList;
        this.favorList = favorList;
        this.anniversaryList = anniversaryList;
    }
}
