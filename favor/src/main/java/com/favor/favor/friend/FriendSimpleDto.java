package com.favor.favor.friend;

import com.favor.favor.photo.UserPhoto;
import com.favor.favor.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FriendSimpleDto {
    private Long friendNo;
    private String friendName;
    private UserPhoto photo;

    @Builder
    public FriendSimpleDto(Friend friend, User friendUser, UserPhoto photo){
        this.friendNo = friend.getFriendNo();
        this.friendName = friendUser.getName();
        this.photo = photo;
    }
}
