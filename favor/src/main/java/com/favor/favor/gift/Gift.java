package com.favor.favor.gift;

import com.favor.favor.common.enums.GiftCategory;
import com.favor.favor.common.enums.Emotion;
import com.favor.favor.common.TimeStamped;
import com.favor.favor.photo.GiftPhoto;
import com.favor.favor.user.User;
import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@ToString
public class Gift extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long giftNo;

    private String giftName;
    public void setGiftName(String giftName){
        this.giftName = giftName;
    }

    @Nullable
    private LocalDate giftDate;
    public void setGiftDate(LocalDate giftDate){
        this.giftDate = giftDate;
    }

    @Nullable
    private String giftMemo;
    public void setGiftMemo(String giftMemo){
        this.giftMemo = giftMemo;
    }

    private Integer category;
    public void setCategory(GiftCategory giftCategory){
        this.category = giftCategory.getType();
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

    @Nullable
    @Builder.Default
    @ElementCollection
    private List<Long> friendNoList = new ArrayList<>();
    public void addFriendNo(Long friendNo){
        friendNoList.add(friendNo);
    }
    public void removeFriendNo(Long friendNo){
        friendNoList.remove(friendNo);
    }


    @OneToMany(cascade = CascadeType.ALL)
    private List<GiftPhoto> giftPhotoList;
    public void setGiftPhotoList(List<GiftPhoto> giftPhotoList) {
        this.giftPhotoList = giftPhotoList;
    }

    @Nullable
    @ElementCollection
    private List<String> tempFriendList;
    public void setTempFriendList(GiftTempFriendListDto tempFriendList){
        this.tempFriendList = tempFriendList.getTempFrindList();
    }
}