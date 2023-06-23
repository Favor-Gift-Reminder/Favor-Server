package com.favor.favor.friend;

import com.favor.favor.friend.Friend;
import com.favor.favor.user.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.transaction.Transactional;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FriendRequestDto {
    @ApiModelProperty(position = 1, required = true, dataType = "Long", value = "회원친구번호", example = "1")
    private Long friendUserNo;
    @Transactional
    public Friend toEntity(User user, User userFriend){
        return Friend.builder()
                .friendUserNo(friendUserNo)
                .friendName(userFriend.getName())
                .friendMemo("")
                .user(user)
                .build();
    }
}
