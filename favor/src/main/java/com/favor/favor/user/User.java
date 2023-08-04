package com.favor.favor.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.favor.favor.anniversary.Anniversary;
import com.favor.favor.common.enums.Favor;
import com.favor.favor.common.enums.Role;
import com.favor.favor.common.TimeStamped;
import com.favor.favor.friend.Friend;
import com.favor.favor.gift.Gift;
import com.favor.favor.photo.Photo;
import com.favor.favor.reminder.Reminder;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Transactional
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

    @OneToOne(cascade = CascadeType.ALL)
    private Photo userProfilePhoto;
    public void setUserProfilePhoto(Photo userPhoto) {
        this.userProfilePhoto = userPhoto;
    }

    @OneToOne(cascade = CascadeType.ALL)
    private Photo userBackgroundPhoto;
    public void setUserBackgroundPhoto(Photo userPhoto) {
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
}
