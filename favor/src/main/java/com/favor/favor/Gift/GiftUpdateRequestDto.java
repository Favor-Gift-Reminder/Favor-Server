package com.favor.favor.Gift;

import com.favor.favor.Friend.Friend;
import com.favor.favor.User.User;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class GiftUpdateRequestDto {
    //giftName, giftDate, giftMemo, category, emotion, isPinned, isGiven 수정 가능
    @ApiModelProperty(position = 1, required = false, dataType = "String", value = "선물이름", example = "선물이름")
    private String giftName;

    @ApiModelProperty(position = 2, required = false, dataType = "LocalDate", value = "선물날짜", example = "")
    private LocalDate giftDate;

    @ApiModelProperty(position = 3, required = false, dataType = "String", value = "선물메모", example = "선물메모")
    private String giftMemo;

    @ApiModelProperty(position = 4, required = false, dataType = "String", value = "카데고리", example = "")
    private String category;

    @ApiModelProperty(position = 5, required = false, dataType = "String", value = "감정", example = "")
    private String emotion;

    @ApiModelProperty(position = 6, required = false, dataType = "Boolean", value = "핀 여부", example = "false")
    private Boolean isPinned;

    @ApiModelProperty(position = 7, required = false, dataType = "Boolean", value = "받은선물 여부", example = "false")
    private Boolean isGiven;

    @ApiModelProperty(position = 8, required = false, dataType = "Friend", value = "친구", example = "")
    private Friend friend;

    @Transactional
    public Gift toEntity(User user, Friend friend){
        return Gift.builder()
                .giftName(giftName)
                .giftDate(giftDate)
                .giftMemo(giftMemo)
                .category(category)
                .emotion(emotion)
                .isPinned(isPinned)
                .isGiven(isGiven)
                .user(user)
                .friend(friend)
                .build();
    }
}
