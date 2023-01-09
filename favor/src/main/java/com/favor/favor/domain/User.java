package com.favor.favor.domain;

import com.favor.favor.Common.FavorType;
import com.favor.favor.Common.TimeStamped;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userNo;

    @NotBlank(message = "이름을 입럭해주세요")
    private String name;

    @NotBlank(message = "이메일을 입력해주세요")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요")
    private String password;

    @Nullable
    private LocalDate birth;


    @ElementCollection
    private List<FavorType> favorList;

    @ElementCollection
    private List<LocalDate> eventList;


    @OneToMany(mappedBy = "owner", orphanRemoval = true)
    private List<Gift> giftList = new ArrayList<>();

    @OneToMany(mappedBy = "owner", orphanRemoval = true)
    private List<Reminder> reminderList;


    @ManyToMany
    @JoinTable(name = "user",
            joinColumns = {@JoinColumn(name = "userNo")},
            inverseJoinColumns = {@JoinColumn(name = "ownerNo")})
    private List<User> userFriendList = new ArrayList<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Friend> friendList = new ArrayList<>();

    @Nullable
    @OneToOne(mappedBy = "relatedUserFriend")
    private Gift gift;
}
