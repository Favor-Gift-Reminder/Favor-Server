package com.favor.favor.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.favor.favor.anniversary.Anniversary;
import com.favor.favor.common.enums.Favor;
import com.favor.favor.common.enums.Role;
import com.favor.favor.common.TimeStamped;
import com.favor.favor.friend.Friend;
import com.favor.favor.gift.Gift;
import com.favor.favor.reminder.Reminder;
import lombok.*;

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
public class User extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userNo;
    private String email;

    private String password;
    public void setPassword(String password){ this.password = password; }

    private String userId;
    public void setUserId(String userId){
        this.userId = userId;
    }

    private String name;
    public void setName(String name) {
        this.name = name;
    }


    @Builder.Default
    @ElementCollection
    private List<Integer> favorList = new ArrayList<>();
    @Transactional
    public void setFavorList(List<Favor> favorList) {
        ArrayList<Integer> favorTypeList = new ArrayList<>();
        for(Favor favor : favorList){
            favorTypeList.add(favor.getType());
        }
        this.favorList = favorTypeList;
    }

    @Builder.Default
    @JsonIgnore
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Anniversary> anniversaryList = new ArrayList<>();

    @Builder.Default
    @JsonIgnore
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Gift> giftList = new ArrayList<>();

    @Builder.Default
    @JsonIgnore
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Reminder> reminderList = new ArrayList<>();

    @Builder.Default
    @JsonIgnore
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Friend> friendList = new ArrayList<>();


    private Role role;
}
