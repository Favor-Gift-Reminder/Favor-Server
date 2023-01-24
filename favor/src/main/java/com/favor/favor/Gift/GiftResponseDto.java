package com.favor.favor.Gift;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@Builder
public class GiftResponseDto {

    private Long giftNo;
    private Boolean isPinned;

    @Builder
    public GiftResponseDto(Gift gift){
        this.giftNo = gift.getGiftNo();
        this.isPinned = gift.getIsPinned();
    }
}
