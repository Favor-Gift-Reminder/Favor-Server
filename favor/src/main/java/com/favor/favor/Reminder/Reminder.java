package com.favor.favor.Reminder;

import com.favor.favor.User.User;
import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

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

    @NotBlank
    private String title;

    @NotNull
    private LocalDate reminderDate;

    @Nullable
    private String reminderMemo;

    @NotNull
    private Boolean isAlarmSet;

    @Nullable
    private LocalDateTime alarmTime;


    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_user_no")
    private User user;


    public void setTitle(String title){
        this.title = title;
    }
    public void setReminderDate(LocalDate reminderDate){
        this.reminderDate = reminderDate;
    }
    public void setIsAlarmSet(Boolean isAlarmSet){
        this.isAlarmSet = isAlarmSet;
    }
    public void setAlarmTime(LocalDateTime alarmTime){
        this.alarmTime = alarmTime;
    }
    public void setReminderMemo(String memo){
        this.reminderMemo = memo;
    }
}
