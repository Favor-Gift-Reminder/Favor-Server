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
    @ApiModelProperty(value = "1", example = "1")
    private final Long userNo;

    @ApiModelProperty(value = "favor@gmail.com", example = "favor@gmail.com")
    private String email;

    @ApiModelProperty(value = "페이버", example = "페이버")
    private String name;

    @ApiModelProperty(value = "favor", example = "favor")
    private String userid;

    @ApiModelProperty(value = "USER", example = "USER")
    private Role role;

    @ApiModelProperty(value = "", example = "[\n" +
            "      {\n" +
            "        \"reminderNo\": 1,\n" +
            "        \"reminderTitle\": \"제목\",\n" +
            "        \"reminderDate\": \"1996-02-29\",\n" +
            "        \"reminderMemo\": \"메모\",\n" +
            "        \"isAlarmSet\": false,\n" +
            "        \"alarmTime\": \"1996-02-29T00:00:00\",\n" +
            "        \"userNo\": 1,\n" +
            "        \"friendNo\": 1\n" +
            "      }\n" +
            "    ]")
    private List<ReminderResponseDto> reminderList;

    @ApiModelProperty(value = "", example = "[\n" +
            "      {\n" +
            "        \"giftNo\": 1,\n" +
            "        \"giftName\": \"선물이름\",\n" +
            "        \"giftDate\": \"1996-02-29\",\n" +
            "        \"giftMemo\": \"선물메모\",\n" +
            "        \"category\": \"생일\",\n" +
            "        \"emotion\": \"기뻐요\",\n" +
            "        \"isPinned\": false,\n" +
            "        \"isGiven\": false,\n" +
            "        \"userNo\": 1,\n" +
            "        \"friendList\": [\n" +
            "          {\n" +
            "            \"isUser\": true,\n" +
            "            \"friendNo\": 1,\n" +
            "            \"friendName\": \"페이버2\",\n" +
            "            \"friendMemo\": \"메모\",\n" +
            "            \"reminderList\": [],\n" +
            "            \"giftList\": [],\n" +
            "            \"favorList\": [],\n" +
            "            \"friendUserNo\": 2,\n" +
            "            \"anniversaryList\": [],\n" +
            "            \"userNo\": 1\n" +
            "          }\n" +
            "        ]\n" +
            "      }\n" +
            "    ]")
    private List<GiftResponseDto> giftList;

    @ApiModelProperty(value = "", example = "[\n" +
            "      {\n" +
            "        \"isUser\": true,\n" +
            "        \"friendNo\": 1,\n" +
            "        \"friendName\": \"페이버2\",\n" +
            "        \"friendMemo\": \"메모\",\n" +
            "        \"reminderList\": [],\n" +
            "        \"giftList\": [],\n" +
            "        \"favorList\": [],\n" +
            "        \"friendUserNo\": 2,\n" +
            "        \"anniversaryList\": [],\n" +
            "        \"userNo\": 1\n" +
            "      },\n" +
            "      {\n" +
            "        \"isUser\": false,\n" +
            "        \"friendNo\": 2,\n" +
            "        \"friendName\": \"이름11\",\n" +
            "        \"friendMemo\": \"메모\",\n" +
            "        \"reminderList\": [],\n" +
            "        \"giftList\": [],\n" +
            "        \"favorList\": [],\n" +
            "        \"friendUserNo\": null,\n" +
            "        \"anniversaryList\": [],\n" +
            "        \"userNo\": 1\n" +
            "      }\n" +
            "    ]")
    private List<FriendResponseDto> friendList;

    @ApiModelProperty(value = "", example = "[\n" +
            "      {\n" +
            "        \"anniversaryNo\": 1,\n" +
            "        \"anniversaryTitle\": \"제목\",\n" +
            "        \"anniversaryDate\": \"1996-02-29\",\n" +
            "        \"isPinned\": false,\n" +
            "        \"userNo\": 1\n" +
            "      },\n" +
            "      {\n" +
            "        \"anniversaryNo\": 2,\n" +
            "        \"anniversaryTitle\": \"제목\",\n" +
            "        \"anniversaryDate\": \"2024-02-29\",\n" +
            "        \"isPinned\": false,\n" +
            "        \"userNo\": 1\n" +
            "      }\n" +
            "    ]")
    private List<AnniversaryResponseDto> anniversaryList;

    @ApiModelProperty(value = "", example = "[\n" +
            "      \"심플한\",\n" +
            "      \"귀여운\"\n" +
            "    ]")
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
