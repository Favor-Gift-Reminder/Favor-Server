package com.favor.favor.Domain;

import com.favor.favor.Common.TimeStamped;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Photo extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long photoNo;

    private String fileName;

    private String folderName;


    //프로필사진용
    @OneToOne
    @JoinColumn(name = "user_no")
    private User profileUser;

    //배경사진용
    @OneToOne
    @JoinColumn(name = "user_no")
    private User backgroundUser;

    //친구프로필사진용
    @OneToOne
    @JoinColumn(name = "friend_no")
    private Friend profileFriend;

    //선물사진들용
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "gift_gift_no")
    private Gift gift;
}
