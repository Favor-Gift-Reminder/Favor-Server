package com.favor.favor.domain;

import com.favor.favor.Common.CategoryType;
import com.favor.favor.Common.FavorType;
import com.favor.favor.Common.TimeStamped;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
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

    @NotBlank
    private String title;

    @Nullable
    private LocalDate giftDate;

    @Nullable
    private String memo;

    @Nullable
    private String emotion;

    @NotBlank
    private Boolean isPinned;


    // 관련 취향 목록
    @ElementCollection
    private List<FavorType> favorList = new ArrayList<>();


    // 선물 보유 회원
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_user_no")
    private User owner;


    // 선물 관련 비회원
    @OneToOne(cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinTable(name = "gift_friend",
            joinColumns = @JoinColumn(name = "gift_gift_no"),
            inverseJoinColumns = @JoinColumn(name = "friend_friend_no"))
    private Friend relatedFriend;

    //선물 관련 회원
    @Nullable
    @OneToOne(cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinTable(name = "gift_user",
            joinColumns = @JoinColumn(name = "gift_gift_no"),
            inverseJoinColumns = @JoinColumn(name = "user_user_no"))
    private User relatedUserFriend;


    @Nullable
    private CategoryType category;


    @OneToMany(mappedBy = "gift")
    private List<Photo> photoList = new ArrayList<>();
}
