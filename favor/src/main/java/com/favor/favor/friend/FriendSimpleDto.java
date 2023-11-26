package com.favor.favor.friend;

import com.favor.favor.photo.UserPhoto;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FriendSimpleDto {
    private Long friendNo;
    private Long friendUserNo;
    private String friendName;
    private UserPhoto userPhoto;

    @Builder
    private FriendSimpleDto(Long friendNo, Long friendUserNo, String friendName, UserPhoto userPhoto){
        this.friendNo = friendNo;
        this.friendUserNo = friendUserNo;
        this.friendName = friendName;
        this.userPhoto = userPhoto;
    }

    public static FriendSimpleDto from(Friend friend, UserPhoto userPhoto){
        return FriendSimpleDto.builder()
                .friendUserNo(friend.getFriendUserNo())
                .friendName(friend.getFriendName())
                .userPhoto(userPhoto)
                .build();
    }
}
