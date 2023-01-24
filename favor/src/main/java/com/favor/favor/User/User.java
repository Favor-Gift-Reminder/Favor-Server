package com.favor.favor.User;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.favor.favor.Common.TimeStamped;
import com.favor.favor.Friend.Friend;
import com.favor.favor.Gift.Gift;
import com.favor.favor.Reminder.Reminder;
import lombok.*;

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

    @NotBlank(message = "이메일을 입력해주세요")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요")
    private String password;

    @NotBlank(message = "아이디를 입력해주세요")
    private String userId;

    @NotBlank(message = "이름을 입럭해주세요")
    private String name;

    @NotNull
    private String role;

    public void setName(String name) {
        this.name = name;
    }
    public void setUserId(String userId){
        this.userId = userId;
    }



    @JsonIgnore
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Reminder> reminderList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Gift> giftList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Friend> friendList = new ArrayList<>();

}
