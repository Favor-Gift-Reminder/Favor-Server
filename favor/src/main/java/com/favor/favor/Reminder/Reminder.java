package com.favor.favor.Reminder;

import com.favor.favor.Friend.Friend;
import com.favor.favor.User.User;
import lombok.*;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    private String title;

    private Date reminderDate;

    private String reminderMemo;

    private Boolean isAlarmSet;

    private Date alarmTime;


    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "user_user_no")
    private User user;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "friend_friend_no")
    private Friend friend;



    public void setTitle(String title){
        this.title = title;
    }
    public void setReminderDate(Date reminderDate){
        this.reminderDate = reminderDate;
    }
    public void setIsAlarmSet(Boolean isAlarmSet){
        this.isAlarmSet = isAlarmSet;
    }
    public void setAlarmTime(Date alarmTime){
        this.alarmTime = alarmTime;
    }
    public void setReminderMemo(String memo){
        this.reminderMemo = memo;
    }
    public void setFriend(Friend friend){
        this.friend = friend;
    }
}
