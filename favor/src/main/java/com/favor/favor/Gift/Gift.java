package com.favor.favor.Gift;

import com.favor.favor.Common.Category;
import com.favor.favor.Common.Emotion;
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

    @Nullable
    private Category category;
    public void setCategory(Category category){
        this.category = category;
    }

    @Nullable
    private Emotion emotion;
    public void setEmotion(Emotion emotion) {
        this.emotion = emotion;
    }


    @NotNull
    private Boolean isPinned;
    public void setIsPinned(Boolean isPinned){
        this.isPinned = isPinned;
    }

    @NotNull
    private Boolean isGiven;
    public void setIsGiven(Boolean isGiven){
        this.isGiven = isGiven;
    }


    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_user_no")
    private User user;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "friend_friend_no")
    private Friend friend;
    public void setFriend(Friend friend){ this.friend = friend; }
}
