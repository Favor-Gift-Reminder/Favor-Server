package com.favor.favor.Domain;

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


    // 비회원 친구 관련 선물
    @OneToOne(mappedBy = "relatedFriend")
    private Gift gift;

//    // 친구가 속한그룹
//    @Nullable
//    private GroupType group;


    // 친구사진
    @OneToOne
    @JoinColumn(name = "friend_photo_photo_no")
    @Nullable
    private Photo friendPhoto;


    // 비회원친구 보유 회원
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_user_no")
    private User user;
}
