package com.favor.favor.User;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.favor.favor.Common.Favor;
import com.favor.favor.Common.Role;
import com.favor.favor.Common.TimeStamped;
import com.favor.favor.Friend.Friend;
import com.favor.favor.Gift.Gift;
import com.favor.favor.Reminder.Reminder;
import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Transactional
public class User extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userNo;

    private String email;

    private String password;

    private String userId;
    public void setUserId(String userId){
        this.userId = userId;
    }

    private String name;
    public void setName(String name) {
        this.name = name;
    }


    @ElementCollection
    private List<Favor> favorList = new ArrayList<>();
    public void setFavorList(List<Favor> favorList) { this.favorList = favorList; }

    @JsonIgnore
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Gift> giftList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Reminder> reminderList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Friend> friendList = new ArrayList<>();


    private Role role;
}
