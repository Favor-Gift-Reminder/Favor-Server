package com.favor.favor.Gift;

import com.favor.favor.Enum.Category;
import com.favor.favor.Enum.Emotion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.annotation.Nullable;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
@Builder
public class GiftResponseDto {

    private Long giftNo;
    private String giftName;
    private LocalDate giftDate;
    private String giftMemo;
    private Category category;
    private Emotion emotion;

    private Boolean isPinned;
    private Boolean isGiven;

    private Long userNo;
    @Nullable
    private Long friendNo;

    @Builder
    public GiftResponseDto(Gift gift){
        this.giftNo = gift.getGiftNo();
        this.giftName = gift.getGiftName();
        this.giftDate = gift.getGiftDate();
        this.giftMemo = gift.getGiftMemo();
        this.category = Category.valueOf(gift.getCategory());
        this.emotion = Emotion.valueOf(gift.getEmotion());
        this.isPinned = gift.getIsPinned();
        this.isGiven = gift.getIsGiven();
        this.userNo = gift.getUser().getUserNo();
        this.friendNo = gift.getFriendNo();
    }
}