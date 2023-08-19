package com.favor.favor.gift;

import com.favor.favor.photo.GiftPhoto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class GiftSimpleDto {
    private Long giftNo;
    private String giftName;
    private LocalDate giftDate;
    private List<GiftPhoto> photoList;

    @Builder
    public GiftSimpleDto(Gift gift){
        this.giftNo = gift.getGiftNo();
        this.giftName = gift.getGiftName();
        this.giftDate = gift.getGiftDate();
        this.photoList = gift.getGiftPhotoList();
    }
}
