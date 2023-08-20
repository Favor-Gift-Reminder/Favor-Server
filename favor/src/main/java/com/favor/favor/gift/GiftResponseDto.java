package com.favor.favor.gift;

import com.favor.favor.common.enums.GiftCategory;
import com.favor.favor.common.enums.Emotion;
import com.favor.favor.friend.FriendSimpleDto;
import com.favor.favor.photo.GiftPhoto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Getter
@AllArgsConstructor
@Builder
@ToString
public class GiftResponseDto {

    private Long giftNo;
    private String giftName;
    private LocalDate giftDate;
    private String giftMemo;
    private GiftCategory giftCategory;
    private Emotion emotion;
    private Boolean isPinned;
    private Boolean isGiven;
    private Long userNo;
    private List<FriendSimpleDto> friendList;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<GiftPhoto> giftPhotoList;
    private List<String> tempFriendList;


    @Builder
    public GiftResponseDto(Gift gift){
        log.info("[GiftResponseDto] 실행");
        this.giftNo = gift.getGiftNo();
        this.giftName = gift.getGiftName();
        this.giftDate = gift.getGiftDate();
        this.giftMemo = gift.getGiftMemo();
        this.giftCategory = GiftCategory.valueOf(gift.getCategory());
        this.emotion = Emotion.valueOf(gift.getEmotion());
        this.isPinned = gift.getIsPinned();
        this.isGiven = gift.getIsGiven();
        this.userNo = gift.getUser().getUserNo();
        this.friendList = new ArrayList<>();
        this.createdAt = gift.getCreatedAt();
        this.modifiedAt = gift.getModifiedAt();
        this.giftPhotoList = gift.getGiftPhotoList();
        this.tempFriendList = gift.getTempFriendList();
        log.info("[GiftResponseDto] 실행 완료");
    }
    @Builder
    public GiftResponseDto(Gift gift, List<FriendSimpleDto> friendList){
        log.info("[GiftResponseDto] 실행");
        this.giftNo = gift.getGiftNo();
        this.giftName = gift.getGiftName();
        this.giftDate = gift.getGiftDate();
        this.giftMemo = gift.getGiftMemo();
        this.giftCategory = GiftCategory.valueOf(gift.getCategory());
        this.emotion = Emotion.valueOf(gift.getEmotion());
        this.isPinned = gift.getIsPinned();
        this.isGiven = gift.getIsGiven();
        this.userNo = gift.getUser().getUserNo();
        this.friendList = friendList;
        this.createdAt = gift.getCreatedAt();
        this.modifiedAt = gift.getModifiedAt();
        this.giftPhotoList = gift.getGiftPhotoList();
        this.tempFriendList = gift.getTempFriendList();
        log.info("[GiftResponseDto] 실행 완료");
    }
}
