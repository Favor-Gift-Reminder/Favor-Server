package com.favor.favor.Gift;

import com.favor.favor.Enum.Category;
import com.favor.favor.Enum.Emotion;
import com.favor.favor.Friend.Friend;
import com.favor.favor.User.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class GiftRequestDto {
    @ApiModelProperty(position = 1, required = true, value = "선물이름", example = "선물이름")
    private String giftName;

    @ApiModelProperty(position = 2, required = false, value = "선물날짜", example = "1996-02-29")
    private LocalDate giftDate;

    @ApiModelProperty(position = 3, required = false, value = "선물메모", example = "선물메모")
    private String giftMemo;

    @ApiModelProperty(position = 4, required = false, value = "카테고리", example = "생일")
    private Category category;

    @ApiModelProperty(position = 5, required = false, value = "감정", example = "기뻐요")
    private Emotion emotion;

    @ApiModelProperty(position = 6, required = false, value = "핀 여부", example = "false")
    private Boolean isPinned;
    @ApiModelProperty(position = 7, required = true, value = "받은선물 여부", example = "false")
    private Boolean isGiven;

    @Transactional
    public Gift toEntity(User user, Friend friend){
        return Gift.builder()
                .giftName(giftName)
                .giftDate(giftDate)
                .giftMemo(giftMemo)
                .category(category.getType())
                .emotion(emotion.getType())
                .isPinned(false)
                .isGiven(isGiven)
                .user(user)
                .friend(friend)
                .build();
    }
}