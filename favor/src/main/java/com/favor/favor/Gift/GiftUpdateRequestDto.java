package com.favor.favor.Gift;

import com.favor.favor.Enum.Category;
import com.favor.favor.Enum.Emotion;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class GiftUpdateRequestDto {
    //giftName, giftDate, giftMemo, category, emotion, isPinned, isGiven 수정 가능
    @ApiModelProperty(position = 1, required = false, value = "선물이름", example = "선물이름")
    private String giftName;

    @ApiModelProperty(position = 2, required = false, value = "선물날짜", example = "1996-02-29")
    private LocalDate giftDate;

    @ApiModelProperty(position = 3, required = false, value = "선물메모", example = "선물메모")
    private String giftMemo;

    @ApiModelProperty(position = 4, required = false, value = "카데고리", example = "생일")
    private Category category;

    @ApiModelProperty(position = 5, required = false, value = "감정", example = "기뻐요")
    private Emotion emotion;

    @ApiModelProperty(position = 6, required = false, value = "핀 여부", example = "false")
    private Boolean isPinned;

    @ApiModelProperty(position = 7, required = false, value = "받은선물 여부", example = "false")
    private Boolean isGiven;

}
