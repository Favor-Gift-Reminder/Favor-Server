package com.favor.favor.Friend;

import com.favor.favor.Common.Group;
import com.favor.favor.Common.TimeStamped;
import com.favor.favor.Gift.Gift;
import com.favor.favor.Reminder.Reminder;
import com.favor.favor.User.User;
import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @NotBlank(message = "친구의 이름을 입력해주세요")
    private String friendName;

    @Nullable
    private Group friendGroup;

    @Nullable
    private String friendMemo;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_user_no")
    private User user;


    @OneToMany(mappedBy = "friend", orphanRemoval = true)
    private List<Gift> giftList = new ArrayList<>();

    @OneToMany(mappedBy = "friend", orphanRemoval = true)
    private List<Reminder> reminderList = new ArrayList<>();


    @NotNull
    private Boolean isUser;

    @Nullable
    private Long userFriendNo;





    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }
    public void setGroup(Group friendGroup) {
        this.friendGroup = friendGroup;
    }
    public void setFriendMemo(String friendMemo) {
        this.friendMemo = friendMemo;
    }
}
