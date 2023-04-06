package com.favor.favor.reminder;

import com.favor.favor.friend.Friend;
import com.favor.favor.user.User;
import lombok.*;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Transactional
public class Reminder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reminderNo;

    private String reminderTitle;
    public void setTitle(String reminderTitle){
        this.reminderTitle = reminderTitle;
    }

    private Date reminderDate;
    public void setReminderDate(Date reminderDate){
        this.reminderDate = reminderDate;
    }

    private String reminderMemo;
    public void setReminderMemo(String memo){
        this.reminderMemo = memo;
    }

    private Boolean isAlarmSet;
    public void setIsAlarmSet(Boolean isAlarmSet){
        this.isAlarmSet = isAlarmSet;
    }

    private Date alarmTime;
    public void setAlarmTime(Date alarmTime){
        this.alarmTime = alarmTime;
    }


    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "user_user_no")
    private User user;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "friend_friend_no")
    private Friend friend;
    public void setFriend(Friend friend){
        this.friend = friend;
    }
}
