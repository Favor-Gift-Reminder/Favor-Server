package com.favor.favor.friend.account;

import com.favor.favor.friend.Friend;
import com.favor.favor.user.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.transaction.Transactional;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FriendUserRequestDto {
    @ApiModelProperty(position = 1, required = true, dataType = "Long", value = "회원친구번호", example = "1")
    private Long friendUserNo;

    @ApiModelProperty(position = 2, required = false, dataType = "String", value = "회원친구메모", example = "메모")
    private String userFriendMemo;

    @Transactional
    public Friend toEntity(User user, User userFriend){
        return Friend.builder()
                .isUser(true)
                .friendUserNo(friendUserNo)
                .friendName(userFriend.getName())
                .friendMemo(userFriendMemo)
                .user(user)
                .build();
    }
}
