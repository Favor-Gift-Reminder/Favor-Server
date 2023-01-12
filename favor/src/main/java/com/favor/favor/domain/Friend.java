package com.favor.favor.domain;

import com.favor.favor.Common.GroupType;
import com.favor.favor.Common.TimeStamped;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Friend extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long friendNo;

    // 비회원친구 보유 회원
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_user_no")
    private User user;

    // 비회원 친구 관련 선물
    @OneToOne(mappedBy = "relatedFriend")
    private Gift gift;

    // 친구그룹
    @Nullable
    private GroupType group;

    // 친구사진
    @OneToOne
    @JoinColumn(name = "friend_photo_photo_no")
    @Nullable
    private Photo friendPhoto;
}
