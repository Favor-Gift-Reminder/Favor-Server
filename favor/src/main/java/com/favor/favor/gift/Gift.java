package com.favor.favor.gift;

import com.favor.favor.common.enums.CategoryGift;
import com.favor.favor.common.enums.Emotion;
import com.favor.favor.common.TimeStamped;
import com.favor.favor.photo.GiftPhoto;
import com.favor.favor.user.User;
import lombok.*;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Transactional
public class Gift extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long giftNo;

    private String giftName;
    public void setGiftName(String giftName){
        this.giftName = giftName;
    }

    private LocalDate giftDate;
    public void setGiftDate(LocalDate giftDate){
        this.giftDate = giftDate;
    }

    private String giftMemo;
    public void setGiftMemo(String giftMemo){
        this.giftMemo = giftMemo;
    }

    private Integer category;
    public void setCategory(CategoryGift categoryGift){
        this.category = categoryGift.getType();
    }

    private Integer emotion;
    public void setEmotion(Emotion emotion) {
        this.emotion = emotion.getType();
    }


    private Boolean isPinned;
    public void setIsPinned(Boolean isPinned){
        this.isPinned = isPinned;
    }

    private Boolean isGiven;
    public void setIsGiven(Boolean isGiven){
        this.isGiven = isGiven;
    }


    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_user_no")
    private User user;
    public void setUser(User user) { this.user = user; }

    @Builder.Default
    @ElementCollection
    private List<Long> friendNoList = new ArrayList<>();
    public void setFriendNoList(List<Long> friendNoList){
        this.friendNoList = friendNoList;
    }

    @OneToMany(cascade = CascadeType.ALL)
    private List<GiftPhoto> giftPhotoList = new ArrayList<>();
    public void setGiftPhotoList(List<GiftPhoto> giftPhotoList) {
        this.giftPhotoList = giftPhotoList;
    }

    @ElementCollection
    private List<String> tempFriendList;
    public void setTempFriendList(GiftTempFriendListDto tempFriendList){
        this.tempFriendList = tempFriendList.getTempFrindList();
    }
}