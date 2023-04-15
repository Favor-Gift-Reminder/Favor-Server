package com.favor.favor.gift;

import com.favor.favor.common.enums.Category;
import com.favor.favor.common.enums.Emotion;
import com.favor.favor.friend.FriendResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Log4j2
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
    private List<FriendResponseDto> friendList;


    @Builder
    public GiftResponseDto(Gift gift){
        log.info("[GiftResponseDto] 실행");
        this.giftNo = gift.getGiftNo();
        this.giftName = gift.getGiftName();
        this.giftDate = gift.getGiftDate();
        this.giftMemo = gift.getGiftMemo();
        this.category = Category.valueOf(gift.getCategory());
        this.emotion = Emotion.valueOf(gift.getEmotion());
        this.isPinned = gift.getIsPinned();
        this.isGiven = gift.getIsGiven();
        this.userNo = gift.getUser().getUserNo();
        this.friendList = new ArrayList<>();
        log.info("[GiftResponseDto] 실행 완료");
    }
    @Builder
    public GiftResponseDto(Gift gift, List<FriendResponseDto> friendList){
        log.info("[GiftResponseDto] 실행");
        this.giftNo = gift.getGiftNo();
        this.giftName = gift.getGiftName();
        this.giftDate = gift.getGiftDate();
        this.giftMemo = gift.getGiftMemo();
        this.category = Category.valueOf(gift.getCategory());
        this.emotion = Emotion.valueOf(gift.getEmotion());
        this.isPinned = gift.getIsPinned();
        this.isGiven = gift.getIsGiven();
        this.userNo = gift.getUser().getUserNo();
        this.friendList = friendList;
        log.info("[GiftResponseDto] 실행 완료");
    }
}
