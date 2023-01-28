package com.favor.favor.Gift;

import com.favor.favor.Common.TimeStamped;
import com.favor.favor.Friend.Friend;
import com.favor.favor.User.User;
import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

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

    @NotBlank
    private String giftName;

    @Nullable
    private LocalDate giftDate;

    @Nullable
    private String giftMemo;

    @Nullable
    private String category;

    @Nullable
    private String emotion;


    @NotNull
    private Boolean isPinned;

    @NotNull
    private Boolean isGiven;


    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_user_no")
    private User user;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "friend_friend_no")
    private Friend friend;


    public void setGiftName(String giftName){
        this.giftName = giftName;
    }
    public void setGiftDate(LocalDate giftDate){
        this.giftDate = giftDate;
    }
    public void setGiftMemo(String giftMemo){
        this.giftMemo = giftMemo;
    }
    public void setCategory(String category){
        this.category = category;
    }
    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }
    public void setIsPinned(Boolean isPinned){
        this.isPinned = isPinned;
    }
    public void setIsGiven(Boolean isGiven){
        this.isGiven = isGiven;
    }
    public void setFriend(Friend friend){ this.friend = friend; }
}