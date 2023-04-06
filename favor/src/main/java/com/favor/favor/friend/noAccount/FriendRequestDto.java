package com.favor.favor.friend.noAccount;

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
public class FriendRequestDto {
    @ApiModelProperty(position = 1, required = true, value = "친구이름", example = "이름")
    private String friendName;

    @ApiModelProperty(position = 2, required = false, value = "친구메모", example = "메모")
    private String friendMemo;

    @Transactional
    public Friend toEntity(User user){
        return Friend.builder()
                .isUser(false)
                .friendName(friendName)
                .friendMemo(friendMemo)
                .user(user)
                .build();
    }
}
