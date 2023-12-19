package com.favor.favor.friend;


import lombok.*;

import com.favor.favor.common.TimeStamped;
import com.favor.favor.reminder.Reminder;
import com.favor.favor.user.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Friend extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long friendNo;

    private String friendName;

    private String friendMemo;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_user_no")
    private User user;

    @OneToMany(mappedBy = "friend", orphanRemoval = true)
    private List<Reminder> reminderList = new ArrayList<>();

    private Long friendUserNo;

    @ElementCollection
    private List<Long> giftNoList = new ArrayList<>();

    @ElementCollection
    private List<Long> anniversaryNoList = new ArrayList<>();

    public void updateFriendMemo(String friendMemo) {
        this.friendMemo = friendMemo;
    }

    public void updateGiftNoList(List<Long> giftNoList){
        this.giftNoList = giftNoList;
    }

    @Builder
    public Friend(Long friendNo, String friendName, String friendMemo, User user, List<Reminder> reminderList, Long friendUserNo, List<Long> giftNoList, List<Long> anniversaryNoList) {
        this.friendNo = friendNo;
        this.friendName = friendName;
        this.friendMemo = friendMemo;
        this.user = user;
        this.reminderList = reminderList;
        this.friendUserNo = friendUserNo;
        this.giftNoList = giftNoList;
        this.anniversaryNoList = anniversaryNoList;
    }
}
