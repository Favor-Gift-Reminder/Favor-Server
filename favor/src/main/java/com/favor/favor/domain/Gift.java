package com.favor.favor.domain;

import com.favor.favor.Common.CategoryType;
import com.favor.favor.Common.FavorType;
import com.favor.favor.Common.TimeStamped;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Gift extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long giftNo;

    private String title;

    private LocalDate giftDate;

    private String memo;

    private String emotion;

    private Boolean isPinned;


    @ElementCollection
    private List<FavorType> favorList = new ArrayList<>();


    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_user_no")
    private User owner;


    @OneToOne(cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinTable(name = "gift_friend",
            joinColumns = @JoinColumn(name = "gift_gift_no"),
            inverseJoinColumns = @JoinColumn(name = "friend_friend_no"))
    private Friend relatedFriend;

    @Nullable
    @OneToOne(cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinTable(name = "gift_user",
            joinColumns = @JoinColumn(name = "gift_gift_no"),
            inverseJoinColumns = @JoinColumn(name = "user_user_no"))
    private User relatedUserFriend;
}
