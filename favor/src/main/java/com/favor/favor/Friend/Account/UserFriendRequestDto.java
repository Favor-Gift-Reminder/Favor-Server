package com.favor.favor.Friend.Account;

import com.favor.favor.Friend.Friend;
import com.favor.favor.User.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.transaction.Transactional;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserFriendRequestDto {
    @ApiModelProperty(position = 1, required = true, dataType = "Long", value = "회원친구번호", example = "1")
    private Long userFriendNo;

    @ApiModelProperty(position = 2, required = false, dataType = "String", value = "회원친구메모", example = "메모")
    private String userFriendMemo;

    @Transactional
    public Friend toEntity(User user){
        return Friend.builder()
                .isUser(true)
                .userFriendNo(userFriendNo)
                .friendName("temp")
                .friendMemo(userFriendMemo)
                .user(user)
                .build();
    }
}
