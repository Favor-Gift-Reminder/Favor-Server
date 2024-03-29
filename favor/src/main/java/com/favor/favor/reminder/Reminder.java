package com.favor.favor.reminder;

import com.favor.favor.friend.Friend;
import com.favor.favor.user.User;
import lombok.*;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Reminder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reminderNo;

    private String reminderTitle;
    public void setTitle(String reminderTitle){
        this.reminderTitle = reminderTitle;
    }

    private LocalDate reminderDate;
    public void setReminderDate(LocalDate reminderDate){
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

    private LocalDateTime alarmTime;
    public void setAlarmTime(LocalDateTime alarmTime){
        this.alarmTime = alarmTime;
    }


    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_user_no")
    private User user;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "friend_friend_no", nullable = true)
    private Friend friend;
    public void setFriend(Friend friend){
        this.friend = friend;
    }
}
