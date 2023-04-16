package com.favor.favor.gift;

import com.favor.favor.common.enums.Category;
import com.favor.favor.common.enums.Emotion;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GiftUpdateRequestDto {
    @ApiModelProperty(position = 1, required = false, value = "선물이름", example = "선물이름")
    private String giftName;

    @ApiModelProperty(position = 2, required = false, value = "선물날짜", example = "1996-02-29")
    private String giftDate;

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

    @ApiModelProperty(position = 8, required = false, value = "연관친구 리스트", example = "[1]")
    private List<Long> friendNoList;

}
