package com.favor.favor.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.favor.favor.anniversary.Anniversary;
import com.favor.favor.anniversary.AnniversaryResponseDto;
import com.favor.favor.common.enums.Favor;
import com.favor.favor.common.enums.Role;
import com.favor.favor.common.TimeStamped;
import com.favor.favor.friend.Friend;
import com.favor.favor.friend.FriendSimpleDto;
import com.favor.favor.gift.Gift;
import com.favor.favor.photo.UserPhoto;
import com.favor.favor.reminder.Reminder;
import com.favor.favor.reminder.ReminderSimpleDto;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class User extends TimeStamped implements UserDetails {

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
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Anniversary> anniversaryList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Gift> giftList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Reminder> reminderList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Friend> friendList = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    private UserPhoto userProfilePhoto;
    public void setUserProfilePhoto(UserPhoto userPhoto) {
        this.userProfilePhoto = userPhoto;
    }

    @OneToOne(cascade = CascadeType.ALL)
    private UserPhoto userBackgroundPhoto;
    public void setUserBackgroundPhoto(UserPhoto userPhoto) {
        this.userBackgroundPhoto = userPhoto;
    }

    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {return null;}
    @Override
    public String getUsername() {return name;}
    @Override
    public boolean isAccountNonExpired() {return false;}
    @Override
    public boolean isAccountNonLocked() {return false;}
    @Override
    public boolean isCredentialsNonExpired() {return false;}
    @Override
    public boolean isEnabled() {return false;}

    // toDto
    public UserResponseDto toDto(User user, List<ReminderSimpleDto> reminderList,
                                 List<FriendSimpleDto> friendList,
                                 List<Favor> favorList,
                                 List<AnniversaryResponseDto> anniversaryList,
                                 HashMap<String, Integer> giftInfo) {
        return UserResponseDto.builder()
                .userNo(user.getUserNo())
                .email(user.getEmail())
                .name(user.getName())
                .userId(user.getUserId())
                .role(user.getRole())
                .givenGift(giftInfo.get("given"))
                .receivedGift(giftInfo.get("received"))
                .totalGift(giftInfo.get("total"))
                .reminderList(reminderList)
                .friendList(friendList)
                .anniversaryList(anniversaryList)
                .favorList(favorList)
                .userProfileUserPhoto(user.getUserProfilePhoto())
                .userBackgroundUserPhoto(user.getUserBackgroundPhoto())
                .build();
    }

}