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

    @ApiModelProperty(position = 2, required = false, dataType = "LocalDate", value = "선물날짜", example = "1996-02-29")
    private LocalDate giftDate;

    @ApiModelProperty(position = 3, required = false, dataType = "String", value = "선물메모", example = "선물메모")
    private String giftMemo;

    @ApiModelProperty(position = 4, required = false, dataType = "String", value = "카데고리", example = "생일")
    private String category;

    @ApiModelProperty(position = 5, required = false, dataType = "String", value = "감정", example = "HAPPY")
    private String emotion;

    @ApiModelProperty(position = 6, required = false, dataType = "Boolean", value = "핀 여부", example = "false")
    private Boolean isPinned;

    @ApiModelProperty(position = 7, required = false, dataType = "Boolean", value = "받은선물 여부", example = "false")
    private Boolean isGiven;
}
