package com.favor.favor.Gift;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@Builder
public class GiftRespondDto {
    private String giftName;
    private LocalDate giftDate;
    private String giftMemo;
    private String category;
    private String emotion;

    private Boolean isPinned;
    private Boolean isGiven;

    @Builder
    public GiftRespondDto(Gift gift){
        this.giftName = gift.getGiftName();
        this.giftDate = gift.getGiftDate();
        this.giftMemo = gift.getGiftMemo();
        this.category = gift.getCategory();
        this.emotion = getEmotion();
        this.isPinned = getIsPinned();
        this.isGiven = getIsGiven();
    }
}
