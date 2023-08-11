package com.favor.favor.gift;

import com.favor.favor.common.enums.CategoryGift;
import com.favor.favor.common.enums.Emotion;
import com.favor.favor.user.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GiftRequestDto {
    @ApiModelProperty(position = 1, required = true, value = "선물이름", example = "선물이름")
    @NotNull
    private String giftName;

    @ApiModelProperty(position = 2, required = false, value = "선물날짜", example = "1996-02-29")
    @NotNull
    private String giftDate;

    @ApiModelProperty(position = 3, required = false, value = "선물메모", example = "선물메모")
    private String giftMemo;

    @ApiModelProperty(position = 4, required = false, value = "카테고리", example = "생일")
    @NotNull
    private CategoryGift categoryGift;

    @ApiModelProperty(position = 5, required = false, value = "감정", example = "기뻐요")
    private Emotion emotion;

    @ApiModelProperty(position = 6, required = false, value = "핀 여부", example = "false")
    @NotNull
    private Boolean isPinned;

    @ApiModelProperty(position = 7, required = false, value = "받은선물 여부", example = "false")
    @NotNull
    private Boolean isGiven;

    @ApiModelProperty(position = 8, required = false, value = "연관친구 리스트", example = "[1]")
    private List<Long> friendNoList;

    @ApiModelProperty(position = 9, required = true, value = "임시친구 리스트", example = "[\"차은우\", \"카리나\"]")
    private List<String> tempFriendList;

    @Transactional
    public Gift toEntity(User user, LocalDate giftDate){
        return Gift.builder()
                .giftName(giftName)
                .giftDate(giftDate)
                .giftMemo(giftMemo)
                .category(categoryGift.getType())
                .emotion(emotion.getType())
                .isPinned(false)
                .isGiven(isGiven)
                .user(user)
                .friendNoList(friendNoList)
                .giftPhotoList(new ArrayList<>())
                .tempFriendList(tempFriendList)
                .build();
    }
}