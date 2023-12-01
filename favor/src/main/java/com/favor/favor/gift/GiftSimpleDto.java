package com.favor.favor.gift;

import com.favor.favor.photo.GiftPhoto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class GiftSimpleDto {
    private Long giftNo;
    private String giftName;
    private LocalDate giftDate;
    private List<GiftPhoto> photoList;
    private Boolean isPinned;
    private Boolean isGiven;

    @Builder
    private GiftSimpleDto(Long giftNo, String giftName, LocalDate giftDate, List<GiftPhoto> photoList, Boolean isPinned, Boolean isGiven){
        this.giftNo = giftNo;
        this.giftName = giftName;
        this.giftDate = giftDate;
        this.photoList = photoList;
        this.isPinned = isPinned;
        this.isGiven = isGiven;
    }

    public static GiftSimpleDto from(Gift gift){
        return GiftSimpleDto.builder()
                .giftNo(gift.getGiftNo())
                .giftName(gift.getGiftName())
                .giftDate(gift.getGiftDate())
                .photoList(gift.getGiftPhotoList())
                .isPinned(gift.getIsPinned())
                .isGiven(gift.getIsGiven())
                .build();
    }

}
