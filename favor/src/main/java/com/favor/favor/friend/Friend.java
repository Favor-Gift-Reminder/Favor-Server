package com.favor.favor.friend;


import com.favor.favor.common.enums.Favor;
import lombok.*;

import com.favor.favor.common.TimeStamped;
import com.favor.favor.reminder.Reminder;
import com.favor.favor.user.User;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Transactional
public class Friend extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long friendNo;

    private String friendName;

    private String friendMemo;
    public void setFriendMemo(String friendMemo) {
        this.friendMemo = friendMemo;
    }


    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "user_user_no")
    private User user;


    @Builder.Default
    @OneToMany(mappedBy = "friend", orphanRemoval = true)
    private List<Reminder> reminderList = new ArrayList<>();

    private Long friendUserNo;

    @Builder.Default
    @ElementCollection
    private List<Long> giftNoList = new ArrayList<>();
    public void setGiftNoList(List<Long> giftNoList){
        this.giftNoList = giftNoList;
    }

    @Builder.Default
    @ElementCollection
    private List<Long> anniversaryNoList = new ArrayList<>();
    public void setAnniversaryNoList(List<Long> anniversaryNoList){
        this.anniversaryNoList = anniversaryNoList;
    }
}
