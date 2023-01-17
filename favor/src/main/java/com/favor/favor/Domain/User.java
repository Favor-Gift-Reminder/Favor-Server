package com.favor.favor.Domain;

import com.favor.favor.Common.FavorType;
import com.favor.favor.Common.Role;
import com.favor.favor.Common.TimeStamped;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
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

    @Nullable
    private LocalDate birth;



    //취향 목록
    @ElementCollection
    private List<FavorType> favorList;

    //기념일 목록
    @ElementCollection
    private Map<String, LocalDate> eventList = new HashMap<>();


    //선물 목록
    @OneToMany(mappedBy = "owner", orphanRemoval = true)
    private List<Gift> giftList = new ArrayList<>();

    //리마인더 목록
    @OneToMany(mappedBy = "owner", orphanRemoval = true)
    private List<Reminder> reminderList = new ArrayList<>();

    //친구 목록
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Friend> friendList = new ArrayList<>();



    @OneToOne
    @JoinColumn(name = "profile_photo_photo_no")
    @Nullable
    private Photo profilePhoto;

    @OneToOne
    @JoinColumn(name = "background_photo_photo_no")
    @Nullable
    private Photo backgroundPhoto;



    @NotNull
    private Role role;
}
