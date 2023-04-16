package com.favor.favor.friend.noAccount;

import com.favor.favor.friend.Friend;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.transaction.Transactional;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FriendUpdateRequestDto {
    @ApiModelProperty(position = 1, required = false, dataType = "String", value = "친구이름", example = "이름")
    private String friendName;

    @ApiModelProperty(position = 2, required = false, dataType = "String", value = "친구메모", example = "메모")
    private String friendMemo;

    @Transactional
    public Friend toEntity(){
        return Friend.builder()
                .friendName(friendName)
                .friendMemo(friendMemo)
                .build();
    }
}
