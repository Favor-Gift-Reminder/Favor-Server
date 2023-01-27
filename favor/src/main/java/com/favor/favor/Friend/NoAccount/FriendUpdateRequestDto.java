package com.favor.favor.Friend.NoAccount;

import com.favor.favor.Friend.Friend;
import com.favor.favor.Reminder.Reminder;
import com.favor.favor.User.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FriendUpdateRequestDto {
    @ApiModelProperty(position = 1, required = false, dataType = "String", value = "친구이름", example = "이름")
    private String friendName;

    @ApiModelProperty(position = 2, required = false, dataType = "String", value = "친구", example = "그룹")
    private String group;

    @ApiModelProperty(position = 3, required = false, dataType = "String", value = "친구메모", example = "메모")
    private String friendMemo;

    @Transactional
    public Friend toEntity(){
        return Friend.builder()
                .friendName(friendName)
                .friendGroup(group)
                .friendMemo(friendMemo)
                .build();
    }
}
